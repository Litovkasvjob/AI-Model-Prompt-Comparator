package com.litovka.prompt.comparator.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filter that adds a unique request ID to the MDC context for each HTTP request.
 * This enables request tracking across log messages.
 */
@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    public static final String REQUEST_ID_KEY = "requestId";
    public static final String USER_KEY = "user";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Generate a unique request ID
            String requestId = UUID.randomUUID().toString().substring(0, 8);
            MDC.put(REQUEST_ID_KEY, requestId);
            
            // Add the request ID as a response header for client-side tracking
            response.addHeader("X-Request-ID", requestId);
            
            // Add user information to MDC if available
            String username = request.getRemoteUser();
            if (username != null) {
                MDC.put(USER_KEY, username);
            } else {
                MDC.put(USER_KEY, "anonymous");
            }
            
            // Log the start of request processing
            log.info("Started processing request: {} {}", request.getMethod(), request.getRequestURI());
            
            // Continue with the filter chain
            filterChain.doFilter(request, response);
        } finally {
            // Log the end of request processing
            log.info("Completed request processing");
            
            // Always clear the MDC context to prevent memory leaks
            MDC.clear();
        }
    }
}