package com.litovka.prompt.comparator.controller;

import com.google.gson.Gson;
import com.litovka.prompt.comparator.config.AIConfigurationProperties;
import com.litovka.prompt.comparator.config.AIModelProperties;
import com.litovka.prompt.comparator.dto.PromptDTO;
import com.litovka.prompt.comparator.service.AISelectorService;
import com.litovka.prompt.comparator.service.AIService;
import com.litovka.prompt.comparator.service.ParameterDisplayService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Map;

/**
 * Controller for AI view operations.
 * Provides endpoints for the AI prompt page.
 */
@Slf4j
@Controller
@RequestMapping("/ai")
@SessionAttributes({"commonConfig", "modelTypes", "parameterDisplayValues"})
public class AIViewController {

    private static final String EMPTY_PROMPT_MESSAGE = "Prompt cannot be empty";
    private static final String EMPTY_MODEL_MESSAGE = "Model is empty";
    private static final String ERROR_MESSAGE_FORMAT = "Failed to generate response for model %s: %s";

    private final AISelectorService serviceSelector;
    private final Map<String, List<String>> parameterDisplayValues;
    private final AIModelProperties aiModelProperties;
    private final AIConfigurationProperties aiConfigurationProperties;

    public AIViewController(AISelectorService serviceSelector,
                            AIModelProperties aiModelProperties,
                            AIConfigurationProperties aiConfigurationProperties,
                            ParameterDisplayService parameterDisplayService) {
        this.aiModelProperties = aiModelProperties;
        this.aiConfigurationProperties = aiConfigurationProperties;
        this.serviceSelector = serviceSelector;
        this.parameterDisplayValues = parameterDisplayService.initializeParameterDisplayValues();
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("modelTypes", aiModelProperties.getModelsList());
        model.addAttribute("parameterDisplayValues", new Gson().toJson(parameterDisplayValues));
        model.addAttribute("commonConfig", aiConfigurationProperties.getCommon());
    }

    /**
     * Generates a response based on the given prompt and model data.
     * Processes the input prompt and model attributes, generates answers, updates the PromptDTO,
     * and returns the appropriate view to display the results.
     *
     * @param promptDTO the data transfer object containing the user's prompt and selected models
     * @param model the model used to add attributes for the view
     * @return the name of the view to be rendered ("ai-prompt")
     */
    @PostMapping("/generate")
    public String generateResponse(@ModelAttribute PromptDTO promptDTO, Model model) {
        if (promptDTO.getModels() == null) {
            promptDTO.setModels(List.of());
        }
        log.info("Generating AI response for prompt with {} models", promptDTO.getModels().size());
        log.trace("Prompt details: text='{}', models={}, temperature={}, maxTokens={}, topP={}", 
                promptDTO.getPrompt(), promptDTO.getModels(), 
                promptDTO.getTemperature(), promptDTO.getMaxTokens(), promptDTO.getTopP());

        List<String> answers = processPrompt(promptDTO);
        promptDTO.setAnswers(answers);
        model.addAttribute("promptDTO", promptDTO);

        log.info("AI response generation completed successfully with {} answers", answers.size());
        return "ai-prompt";
    }

    /**
     * Display the AI prompt page.
     *
     * @param model the model to add attributes to
     * @return the view name
     */
    @GetMapping("/prompt")
    public String showPromptPage(Model model) {
        log.info("Displaying AI prompt page with {} available models", aiModelProperties.getModelsList().size());
        return "ai-prompt";
    }

    private List<String> processPrompt(PromptDTO promptDTO) {
        if (!isValidPrompt(promptDTO.getPrompt())) {
            log.warn("Empty prompt received");
            return List.of(EMPTY_PROMPT_MESSAGE);
        }

        if (promptDTO.getModels().isEmpty()) {
            log.warn("No models selected");
            return List.of(EMPTY_MODEL_MESSAGE);
        }

        log.debug("Processing prompt '{}...' with {} models", 
                promptDTO.getPrompt().substring(0, Math.min(20, promptDTO.getPrompt().length())) + "...", 
                promptDTO.getModels().size());
        return promptDTO.getModels().stream()
                .map(model -> generateModelResponse(promptDTO, model))
                .toList();
    }

    private boolean isValidPrompt(String prompt) {
        return prompt != null && !prompt.trim().isEmpty();
    }

    private String generateModelResponse(PromptDTO promptDTO, String model) {
        log.debug("Generating response for model: {} with parameters: temp={}, maxTokens={}", 
                model, promptDTO.getTemperature(), promptDTO.getMaxTokens());

        if (!isValidModel(model)) {
            log.warn("Invalid model: {}", model);
            return EMPTY_MODEL_MESSAGE;
        }

        try {
            log.trace("Getting AI service for model: {}", model);
            AIService aiService = serviceSelector.getServiceBasedOnModel(model);

            log.trace("Calling AI service to generate response for prompt: '{}'", 
                    promptDTO.getPrompt().substring(0, Math.min(30, promptDTO.getPrompt().length())) + "...");
            String response = aiService.generateResponse(promptDTO, model);

            log.trace("Transforming AI response of length: {}", response.length());
            return transformAnswer(response);
        } catch (Exception e) {
            log.error("Error generating response for model: {} - {}", model, e.getMessage(), e);
            return String.format(ERROR_MESSAGE_FORMAT, model, e.getMessage());
        }
    }

    private boolean isValidModel(String model) {
        return model != null && !model.isBlank() && !model.contains("none");
    }

    @NotNull
    private static String transformAnswer(String answer) {
        return answer.replaceAll("(\\.|!|\\?) ", "$1\n");
    }

}
