package com.litovka.prompt.comparator.service.impl;

import com.litovka.prompt.comparator.config.AIConfigurationProperties;
import com.litovka.prompt.comparator.dto.PromptDTO;
import com.litovka.prompt.comparator.exception.ClaudeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ClaudeService.
 * Tests the Claude-specific implementation of the AIService interface.
 */
@ExtendWith(MockitoExtension.class)
public class ClaudeServiceTest {

    @Mock
    private AIConfigurationProperties aiConfigurationProperties;

    private ClaudeService claudeService;

    @BeforeEach
    public void setup() {
        claudeService = new ClaudeService(aiConfigurationProperties);
    }

    @Test
    public void testGetModelType() {
        assertEquals("Claude", claudeService.getModelType());
    }

    @Test
    public void testCreateException() {
        String errorMessage = "Test error";
        Exception cause = new RuntimeException("Test cause");
        
        RuntimeException exception = claudeService.createException(errorMessage, cause);
        
        assertTrue(exception instanceof ClaudeException);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    // Note: We're not testing callModelApi directly because it requires mocking static methods
    // and complex builder patterns. Instead, we're focusing on testing the public methods
    // and the behavior of the service.
}