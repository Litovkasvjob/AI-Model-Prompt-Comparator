package com.litovka.prompt.comparator.exception;

/**
 * Exception thrown when there is an error with the Anthropic AI service.
 */
public class ClaudeException extends AIException {

    private static final String SERVICE_NAME = "Claude";

    /**
     * Constructs a new ClaudeException with the specified detail message.
     *
     * @param message the detail message
     */
    public ClaudeException(String message) {
        super(SERVICE_NAME, message);
    }

    /**
     * Constructs a new ClaudeException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public ClaudeException(String message, Throwable cause) {
        super(SERVICE_NAME, message, cause);
    }

    /**
     * Constructs a new ClaudeException with the specified cause.
     *
     * @param cause the cause
     */
    public ClaudeException(Throwable cause) {
        super(SERVICE_NAME, cause);
    }
}
