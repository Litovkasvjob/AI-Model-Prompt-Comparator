package com.litovka.prompt.comparator.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GeminiException.
 * Tests the functionality of the exception class for Gemini service related errors.
 */
public class GeminiExceptionTest {

    private static final String EXPECTED_SERVICE_NAME = "Gemini";
    private static final String ERROR_MESSAGE = "Test Gemini error message";
    private static final Throwable CAUSE = new RuntimeException("Test Gemini cause");

    @Test
    public void testConstructorWithMessage() {
        GeminiException exception = new GeminiException(ERROR_MESSAGE);
        
        assertEquals(EXPECTED_SERVICE_NAME, exception.getServiceName());
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        GeminiException exception = new GeminiException(ERROR_MESSAGE, CAUSE);
        
        assertEquals(EXPECTED_SERVICE_NAME, exception.getServiceName());
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    public void testConstructorWithCause() {
        GeminiException exception = new GeminiException(CAUSE);
        
        assertEquals(EXPECTED_SERVICE_NAME, exception.getServiceName());
        // When only cause is provided, the message is typically the cause's toString
        assertTrue(exception.getMessage().contains(CAUSE.toString()));
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    public void testInheritanceFromAIException() {
        GeminiException exception = new GeminiException(ERROR_MESSAGE);
        
        assertTrue(exception instanceof AIException);
    }
}