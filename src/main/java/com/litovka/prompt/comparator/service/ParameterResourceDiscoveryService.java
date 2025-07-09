package com.litovka.prompt.comparator.service;

import com.litovka.prompt.comparator.config.ParameterDisplayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service responsible for discovering resources related to parameter files
 * and processing their metadata within the application.
 * This service is conditionally enabled based on the property `parameter.display.enabled`.
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "parameter.display.enabled", havingValue = "true", matchIfMissing = true)
public class ParameterResourceDiscoveryService {

    private final ParameterDisplayProperties properties;
    private final PathMatchingResourcePatternResolver resourceResolver;

    public ParameterResourceDiscoveryService(ParameterDisplayProperties properties) {
        this.properties = properties;
        this.resourceResolver = new PathMatchingResourcePatternResolver();
    }

    /**
     * Discovers all parameter property files and returns their base names for MessageSource
     */
    public List<String> discoverParameterBasenames() {
        List<String> basenames = new ArrayList<>();
        
        try {
            Resource[] resources = resourceResolver.getResources(properties.getFullPattern());
            
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename != null && filename.endsWith(".properties")) {
                    // Remove .properties extension to get basename
                    String basename = properties.getBasePath() + "/" + 
                                    filename.substring(0, filename.lastIndexOf('.'));
                    basenames.add(basename);
                }
            }
        } catch (IOException e) {
            log.warn("Could not discover parameter files with pattern: {}", properties.getFullPattern(), e);
        }
        
        log.info("Discovered {} parameter basenames", basenames.size());
        return basenames;
    }

    /**
     * Discovers all parameter property resources
     */
    public List<Resource> discoverParameterResources() {
        try {
            Resource[] resources = resourceResolver.getResources(properties.getFullPattern());
            return Arrays.asList(resources);
        } catch (IOException e) {
            log.warn("Could not discover parameter resources with pattern: {}", properties.getFullPattern(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Checks if a property key is a parameter key
     */
    public boolean isParameterKey(String key) {
        return key != null && key.startsWith(properties.getKeyPrefix());
    }

    /**
     * Extracts category from parameter key (e.g., "parameter_.activity.something" -> "activity")
     */
    public String extractCategory(String parameterKey) {
        if (!isParameterKey(parameterKey)) {
            return null;
        }
        
        String[] keyParts = parameterKey.split("\\.");
        if (keyParts.length >= 2) {
            String rawCategory = keyParts[1];
            // Check if there's a custom mapping for this category
            return properties.getCategoryMapping().getOrDefault(rawCategory, rawCategory);
        }
        
        return null;
    }
}