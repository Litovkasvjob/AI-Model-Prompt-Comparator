package com.litovka.prompt.comparator.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AIExceptionHandler.
 * Tests the exception handling functionality for AI service related exceptions.
 */
public class AIExceptionHandlerTest {

    private AIExceptionHandler exceptionHandler;
    
    @BeforeEach
    public void setup() {
        exceptionHandler = new AIExceptionHandler();
    }
    
    @Test
    public void testHandleAIException() {
        // Arrange
        String serviceName = "TestService";
        String errorMessage = "Test error message";
        AIException exception = new AIException(serviceName, errorMessage);
        
        // Act
        ResponseEntity<String> response = exceptionHandler.handleAIException(exception);
        
        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertTrue(response.getBody().contains(serviceName));
        assertTrue(response.getBody().contains(errorMessage));
    }
    
    @Test
    public void testHandleGPTException() {
        // Arrange
        String errorMessage = "GPT error message";
        GPTException exception = new GPTException(errorMessage);
        
        // Act
        ResponseEntity<String> response = exceptionHandler.handleGPTException(exception);
        
        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertTrue(response.getBody().contains("GPT"));
        assertTrue(response.getBody().contains(errorMessage));
    }
    
    @Test
    public void testHandleGeminiException() {
        // Arrange
        String errorMessage = "Gemini error message";
        GeminiException exception = new GeminiException(errorMessage);
        
        // Act
        ResponseEntity<String> response = exceptionHandler.handleGeminiException(exception);
        
        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertTrue(response.getBody().contains("Gemini"));
        assertTrue(response.getBody().contains(errorMessage));
    }
    
    @Test
    public void testHandleClaudeException() {
        // Arrange
        String errorMessage = "Claude error message";
        ClaudeException exception = new ClaudeException(errorMessage);
        
        // Act
        ResponseEntity<String> response = exceptionHandler.handleClaudeException(exception);
        
        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertTrue(response.getBody().contains("Claude"));
        assertTrue(response.getBody().contains(errorMessage));
    }
    
    @Test
    public void testExceptionWithCause() {
        // Arrange
        String serviceName = "TestService";
        String errorMessage = "Test error with cause";
        Throwable cause = new RuntimeException("Underlying cause");
        AIException exception = new AIException(serviceName, errorMessage, cause);
        
        // Act
        ResponseEntity<String> response = exceptionHandler.handleAIException(exception);
        
        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertTrue(response.getBody().contains(serviceName));
        assertTrue(response.getBody().contains(errorMessage));
    }
}