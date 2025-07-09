package com.litovka.prompt.comparator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration properties for managing parameter display settings in the application.
 *
 * This class is annotated with Spring's @ConfigurationProperties annotation, enabling
 * the externalized configuration of properties defined with the prefix "parameter.display".
 * Designed to support parameter file integration, caching, and encoding for managing
 * parameters dynamically within the application.
 *
 * Key attributes include:
 * - Base path and file pattern for locating parameter files.
 * - A configurable key prefix to standardize parameter keys.
 * - Cache settings to control the duration for reloading parameter resources.
 * - Encoding settings to standardize character encoding for parameter files.
 * - A toggle to enable or disable the parameter display service.
 * - Support for mapping custom parameter categories.
 *
 * This class also provides utility methods like `getFullPattern` to calculate
 * the full classpath pattern for parameter files based on the base path and file pattern.
 */
@Data
@ConfigurationProperties(prefix = "parameter.display")
public class ParameterDisplayProperties {
    
    /**
     * Base path for parameter files
     */
    private String basePath = "suggestions";
    
    /**
     * File pattern for parameter files
     */
    private String filePattern = "parameter_*.properties";
    
    /**
     * Key prefix for parameter keys
     */
    private String keyPrefix = "parameter_";
    
    /**
     * Cache duration in seconds for MessageSource
     */
    private int cacheSeconds = 3600;
    
    /**
     * Default encoding for parameter files
     */
    private String encoding = "UTF-8";
    
    /**
     * Enable/disable parameter display service
     */
    private boolean enabled = true;

    /**
     * Custom parameter categories mapping
     */
    private Map<String, String> categoryMapping = new HashMap<>();


    /**
     * Get the full classpath pattern for parameter files
     */
    public String getFullPattern() {
        return "classpath:" + basePath + "/" + filePattern;
    }
}