package com.litovka.prompt.comparator.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ClaudeException.
 * Tests the functionality of the exception class for Claude service related errors.
 */
public class ClaudeExceptionTest {

    private static final String EXPECTED_SERVICE_NAME = "Claude";
    private static final String ERROR_MESSAGE = "Test Claude error message";
    private static final Throwable CAUSE = new RuntimeException("Test Claude cause");

    @Test
    public void testConstructorWithMessage() {
        ClaudeException exception = new ClaudeException(ERROR_MESSAGE);
        
        assertEquals(EXPECTED_SERVICE_NAME, exception.getServiceName());
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        ClaudeException exception = new ClaudeException(ERROR_MESSAGE, CAUSE);
        
        assertEquals(EXPECTED_SERVICE_NAME, exception.getServiceName());
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    public void testConstructorWithCause() {
        ClaudeException exception = new ClaudeException(CAUSE);
        
        assertEquals(EXPECTED_SERVICE_NAME, exception.getServiceName());
        // When only cause is provided, the message is typically the cause's toString
        assertTrue(exception.getMessage().contains(CAUSE.toString()));
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    public void testInheritanceFromAIException() {
        ClaudeException exception = new ClaudeException(ERROR_MESSAGE);
        
        assertTrue(exception instanceof AIException);
    }
}