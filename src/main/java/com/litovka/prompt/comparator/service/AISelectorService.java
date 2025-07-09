package com.litovka.prompt.comparator.service;

import com.litovka.prompt.comparator.service.impl.ClaudeService;
import com.litovka.prompt.comparator.service.impl.GPTService;
import com.litovka.prompt.comparator.service.impl.GeminiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AIServiceSelector is responsible for selecting the appropriate implementation of the AIService interface
 * based on a given model name. It provides a mechanism to route the request to the relevant AI service,
 * such as GPTService, GeminiService, or ClaudeService, by analyzing the specified model name.
 *
 * The selection occurs as follows:
 * - If the model name contains "gemini" (case insensitive), GeminiService is selected.
 * - If the model name contains "claude" (case insensitive), ClaudeService is selected.
 * - If neither condition is met, the default GPTService is selected.
 *
 * This class relies on Spring's dependency injection to manage the instances of GPTService,
 * GeminiService, and ClaudeService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AISelectorService {

    public static final String GEMINI = "gemini";
    public static final String CLAUDE = "claude";

    private final GPTService gptService;
    private final GeminiService geminiService;
    private final ClaudeService claudeService;

    /**
     * Determines and returns the appropriate AIService implementation based on the specified model name.
     * If the model name contains "gemini" (case insensitive), the GeminiService is returned.
     * If the model name contains "claude" (case insensitive), the ClaudeService is returned.
     * If no match is found, the default GPTService is returned.
     *
     * @param modelName the name of the AI model as a string. This parameter is used to select the corresponding AI service.
     *                  It can be null or case insensitive.
     * @return the appropriate implementation of the AIService interface based on the model name.
     *         Defaults to GPTService if no match is found.
     */
    public AIService getServiceBasedOnModel(String modelName) {
        log.info("Selecting AI service based on model name: {}", modelName);

        AIService selectedService;
        String serviceType;

        if (modelName != null && modelName.toLowerCase().contains(GEMINI)) {
            selectedService = geminiService;
            serviceType = "Gemini";
        } else if (modelName != null && modelName.toLowerCase().contains(CLAUDE)) {
            selectedService = claudeService;
            serviceType = "Claude";
        } else {
            selectedService = gptService;
            serviceType = "GPT (default)";
        }

        log.debug("Selected {} service for model: {}, implementation: {}", 
                serviceType, modelName, selectedService.getClass().getSimpleName());
        return selectedService;
    }
}
