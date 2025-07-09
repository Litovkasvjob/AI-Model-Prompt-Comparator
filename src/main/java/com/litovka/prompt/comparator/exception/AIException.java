package com.litovka.prompt.comparator.exception;

/**
 * Base exception class for all AI service related exceptions.
 * This class serves as a common parent for all specific AI service exceptions
 * like GPTException, GeminiException, and ClaudeException.
 */
public class AIException extends RuntimeException {

    /**
     * The name of the AI service that caused the exception.
     */
    private final String serviceName;

    /**
     * Constructs a new AIException with the specified service name and detail message.
     *
     * @param serviceName the name of the AI service
     * @param message the detail message
     */
    public AIException(String serviceName, String message) {
        super(message);
        this.serviceName = serviceName;
    }

    /**
     * Constructs a new AIException with the specified service name, detail message, and cause.
     *
     * @param serviceName the name of the AI service
     * @param message the detail message
     * @param cause the cause
     */
    public AIException(String serviceName, String message, Throwable cause) {
        super(message, cause);
        this.serviceName = serviceName;
    }

    /**
     * Constructs a new AIException with the specified service name and cause.
     *
     * @param serviceName the name of the AI service
     * @param cause the cause
     */
    public AIException(String serviceName, Throwable cause) {
        super(cause);
        this.serviceName = serviceName;
    }

    /**
     * Returns the name of the AI service that caused the exception.
     *
     * @return the service name
     */
    public String getServiceName() {
        return serviceName;
    }
}