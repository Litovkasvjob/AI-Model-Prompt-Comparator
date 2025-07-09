package com.litovka.prompt.comparator.service.impl;

import com.litovka.prompt.comparator.dto.PromptDTO;
import com.litovka.prompt.comparator.service.AIService;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base class for AI service implementations.
 * This class encapsulates common behavior shared by all AI service implementations,
 * such as logging, error handling, and the basic flow of generating responses.
 * Specific AI service implementations should extend this class and implement
 * the abstract methods to provide their unique functionality.
 */
@Slf4j
public abstract class BaseAIService implements AIService {

    @Override
    public String generateResponse(PromptDTO promptDTO, String model) {
        log.info("Generating response using {} model: {} with prompt length: {}", 
                getModelType(), model, promptDTO.getPrompt().length());
        log.debug("Prompt parameters: temperature={}, maxTokens={}, topP={}, prompt preview: '{}'", 
                promptDTO.getTemperature(), promptDTO.getMaxTokens(), promptDTO.getTopP(),
                promptDTO.getPrompt().substring(0, Math.min(50, promptDTO.getPrompt().length())) + "...");

        try {
            log.trace("Preparing request payload for {} API", getModelType());
            log.debug("Sending request to {} API endpoint", getModelType());

            long startTime = System.currentTimeMillis();
            String response = callModelApi(promptDTO, model);
            long duration = System.currentTimeMillis() - startTime;

            log.info("Successfully received response from {} model in {}ms, response length: {}", 
                    getModelType(), duration, response.length());
            return response;
        } catch (Exception e) {
            log.error("Error generating response from {} model: {}", getModelType(), e.getMessage(), e);
            throw createException("Error generating response from " + getModelType(), e);
        }
    }

    /**
     * Returns the type of the AI model being used.
     * This is used for logging purposes.
     *
     * @return the model type as a string
     */
    protected abstract String getModelType();

    /**
     * Calls the specific AI model API to generate a response.
     * This method should be implemented by each specific AI service implementation.
     *
     * @param promptDTO the prompt data transfer object containing the prompt and parameters
     * @param model the specific model to use
     * @return the generated response as a string
     */
    protected abstract String callModelApi(PromptDTO promptDTO, String model);

    /**
     * Creates a specific exception for the AI service.
     * This method should be implemented by each specific AI service implementation.
     *
     * @param message the error message
     * @param cause the cause of the exception
     * @return a RuntimeException specific to the AI service
     */
    protected abstract RuntimeException createException(String message, Exception cause);
}
