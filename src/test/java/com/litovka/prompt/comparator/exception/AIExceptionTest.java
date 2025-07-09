package com.litovka.prompt.comparator.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AIException.
 * Tests the functionality of the base exception class for AI service related exceptions.
 */
public class AIExceptionTest {

    private static final String SERVICE_NAME = "TestService";
    private static final String ERROR_MESSAGE = "Test error message";
    private static final Throwable CAUSE = new RuntimeException("Test cause");

    @Test
    public void testConstructorWithServiceNameAndMessage() {
        AIException exception = new AIException(SERVICE_NAME, ERROR_MESSAGE);
        
        assertEquals(SERVICE_NAME, exception.getServiceName());
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    public void testConstructorWithServiceNameMessageAndCause() {
        AIException exception = new AIException(SERVICE_NAME, ERROR_MESSAGE, CAUSE);
        
        assertEquals(SERVICE_NAME, exception.getServiceName());
        assertEquals(ERROR_MESSAGE, exception.getMessage());
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    public void testConstructorWithServiceNameAndCause() {
        AIException exception = new AIException(SERVICE_NAME, CAUSE);
        
        assertEquals(SERVICE_NAME, exception.getServiceName());
        // When only cause is provided, the message is typically the cause's toString
        assertTrue(exception.getMessage().contains(CAUSE.toString()));
        assertEquals(CAUSE, exception.getCause());
    }

    @Test
    public void testGetServiceName() {
        AIException exception = new AIException(SERVICE_NAME, ERROR_MESSAGE);
        assertEquals(SERVICE_NAME, exception.getServiceName());
        
        // Test with a different service name
        String differentServiceName = "DifferentService";
        AIException anotherException = new AIException(differentServiceName, ERROR_MESSAGE);
        assertEquals(differentServiceName, anotherException.getServiceName());
    }
}