# Logging Strategy

This document outlines the logging strategy implemented in the AI Model Prompt Comparator Service.

## Overview

The application uses a structured logging approach with the following features:

1. **Request Tracking with MDC**: Each request is assigned a unique ID that is included in all log messages related to that request.
2. **User Context**: User information is included in log messages when available.
3. **Appropriate Log Levels**: Log levels are carefully chosen to ensure the right balance of information:
   - `ERROR`: For errors that affect functionality
   - `WARN`: For potential issues that don't stop execution
   - `INFO`: For significant events in the application lifecycle
   - `DEBUG`: For detailed information useful during development
   - `TRACE`: For very detailed information about internal processing steps

## Implementation Details

### Request ID Tracking

A `RequestLoggingFilter` adds a unique request ID to the MDC (Mapped Diagnostic Context) for each HTTP request. This ID is included in all log messages related to that request, making it easy to trace the flow of a request through the system.

```
// Example from RequestLoggingFilter
String requestId = UUID.randomUUID().toString().substring(0, 8);
MDC.put(REQUEST_ID_KEY, requestId);
```

### Log Format

The log format includes timestamp, thread name, request ID, user information, log level, logger name, and message:

```
%d{yyyy-MM-dd HH:mm:ss} [%thread] [%X{requestId:-}] [%X{user:-}] %-5level %logger{36} - %msg%n
```

### Contextual Information

Log messages include relevant contextual information to aid in debugging and monitoring:

- Model information (type, name, implementation)
- Prompt details (length, preview, parameters)
- Performance metrics (response time)
- Response information (length, status)

### Log Level Guidelines

- **ERROR**: Use for exceptions and errors that affect functionality
- **WARN**: Use for potential issues that don't stop execution
- **INFO**: Use for significant events (request start/end, service selection, API calls)
- **DEBUG**: Use for detailed information useful during development
- **TRACE**: Use for very detailed information about internal processing steps

## Configuration

Log levels can be configured in `application.properties`:

```properties
# Root logger level
logging.level.root=INFO

# Application package level
logging.level.com.litovka.prompt.comparator=DEBUG

# Specific class levels
logging.level.com.litovka.prompt.comparator.controller.AIViewController=TRACE
logging.level.com.litovka.prompt.comparator.service.impl.BaseAIService=TRACE
```

## Best Practices

1. **Be Consistent**: Follow the established patterns when adding new logging statements
2. **Include Context**: Always include relevant context in log messages
3. **Choose Appropriate Levels**: Use the right log level for the information being logged
4. **Protect Sensitive Data**: Never log sensitive information (API keys, user credentials, etc.)
5. **Performance Awareness**: Be mindful of the performance impact of logging, especially at TRACE level
