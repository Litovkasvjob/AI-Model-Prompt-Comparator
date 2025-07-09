package com.litovka.prompt.comparator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Unified exception handler for all AI service related exceptions.
 * This handler replaces the individual handlers for GPT, Gemini, and Claude exceptions.
 */
@Slf4j
@ControllerAdvice
public class AIExceptionHandler {

    /**
     * Handles all AIException and its subclasses.
     * 
     * @param ex the exception to handle
     * @return a ResponseEntity with an appropriate error message
     */
    @ExceptionHandler(AIException.class)
    public ResponseEntity<String> handleAIException(AIException ex) {
        log.error("{} API error occurred: {}", ex.getServiceName(), ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Error processing " + ex.getServiceName() + " request: " + ex.getMessage());
    }
    
    /**
     * Specific handler for GPTException for backward compatibility.
     * This method delegates to the general AIException handler.
     * 
     * @param ex the exception to handle
     * @return a ResponseEntity with an appropriate error message
     */
    @ExceptionHandler(GPTException.class)
    public ResponseEntity<String> handleGPTException(GPTException ex) {
        return handleAIException(ex);
    }
    
    /**
     * Specific handler for GeminiException for backward compatibility.
     * This method delegates to the general AIException handler.
     * 
     * @param ex the exception to handle
     * @return a ResponseEntity with an appropriate error message
     */
    @ExceptionHandler(GeminiException.class)
    public ResponseEntity<String> handleGeminiException(GeminiException ex) {
        return handleAIException(ex);
    }
    
    /**
     * Specific handler for ClaudeException for backward compatibility.
     * This method delegates to the general AIException handler.
     * 
     * @param ex the exception to handle
     * @return a ResponseEntity with an appropriate error message
     */
    @ExceptionHandler(ClaudeException.class)
    public ResponseEntity<String> handleClaudeException(ClaudeException ex) {
        return handleAIException(ex);
    }
}