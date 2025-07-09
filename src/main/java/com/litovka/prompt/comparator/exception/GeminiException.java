package com.litovka.prompt.comparator.exception;

/**
 * Exception thrown when there is an error with the Google Gemini AI service.
 */
public class GeminiException extends AIException {

    private static final String SERVICE_NAME = "Gemini";

    /**
     * Constructs a new GeminiException with the specified detail message.
     *
     * @param message the detail message
     */
    public GeminiException(String message) {
        super(SERVICE_NAME, message);
    }

    /**
     * Constructs a new GeminiException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public GeminiException(String message, Throwable cause) {
        super(SERVICE_NAME, message, cause);
    }

    /**
     * Constructs a new GeminiException with the specified cause.
     *
     * @param cause the cause
     */
    public GeminiException(Throwable cause) {
        super(SERVICE_NAME, cause);
    }
}
