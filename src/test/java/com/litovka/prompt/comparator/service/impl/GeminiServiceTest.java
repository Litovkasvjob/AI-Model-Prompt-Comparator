package com.litovka.prompt.comparator.service.impl;

import com.litovka.prompt.comparator.config.AIConfigurationProperties;
import com.litovka.prompt.comparator.dto.PromptDTO;
import com.litovka.prompt.comparator.exception.GeminiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GeminiService.
 * Tests the Gemini-specific implementation of the AIService interface.
 */
@ExtendWith(MockitoExtension.class)
public class GeminiServiceTest {

    @Mock
    private AIConfigurationProperties aiConfigurationProperties;

    private GeminiService geminiService;

    @BeforeEach
    public void setup() {
        geminiService = new GeminiService(aiConfigurationProperties);
    }

    @Test
    public void testGetModelType() {
        assertEquals("Gemini", geminiService.getModelType());
    }

    @Test
    public void testCreateException() {
        String errorMessage = "Test error";
        Exception cause = new RuntimeException("Test cause");
        
        RuntimeException exception = geminiService.createException(errorMessage, cause);
        
        assertTrue(exception instanceof GeminiException);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    // Note: We're not testing callModelApi directly because it requires mocking static methods
    // and complex builder patterns. Instead, we're focusing on testing the public methods
    // and the behavior of the service.
}