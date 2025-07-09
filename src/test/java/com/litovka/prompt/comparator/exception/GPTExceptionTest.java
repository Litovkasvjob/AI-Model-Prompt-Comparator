package com.litovka.prompt.comparator.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GPTException.
 * Tests the functionality of the exception class for GPT service related errors.
 */
public class GPTExceptionTest {

    private static final String EXPECTED_SERVICE_NAME = "GPT";
    private static final String ERROR_MESSAGE = "Test GPT error message";
    private static final Throwable CAUSE = new RuntimeException("Test GPT cause");

    @Test
    public void testConstructorWithMessage() {
        GPTException exception = new GPTException(ERROR_MESSAGE);
        
        assertEquals(EXPECTED_SERVICE_NAME, exception.getServiceName());
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        GPTException exception = new GPTException(ERROR_MESSAGE, CAUSE);
        
        assertEquals(EXPECTED_SERVICE_NAME, exception.getServiceName());
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    public void testConstructorWithCause() {
        GPTException exception = new GPTException(CAUSE);
        
        assertEquals(EXPECTED_SERVICE_NAME, exception.getServiceName());
        // When only cause is provided, the message is typically the cause's toString
        assertTrue(exception.getMessage().contains(CAUSE.toString()));
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    public void testInheritanceFromAIException() {
        GPTException exception = new GPTException(ERROR_MESSAGE);
        
        assertTrue(exception instanceof AIException);
    }
}