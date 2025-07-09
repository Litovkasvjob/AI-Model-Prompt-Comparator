package com.litovka.prompt.comparator.service;

import com.litovka.prompt.comparator.config.ParameterDisplayProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Service responsible for initializing and organizing parameter display values
 * from specified resource files. This service is conditionally enabled based on
 * the "parameter.display.enabled" property in the application configuration.
 *
 * The service reads properties from parameter resources, processes the keys based
 * on certain patterns, groups them into categories, and localizes their values
 * using the configured message source.
 *
 * An unmodifiable, sorted map of categorized and localized parameter values is
 * created as the output.
 *
 * The service depends on the following components:
 * 1. {@link MessageSource} for retrieving localized messages for parameter keys.
 * 2. {@link ParameterResourceDiscoveryService} for discovering and validating
 *    parameter resource files.
 * 3. {@link ParameterDisplayProperties} for configuration properties needed by
 *    the service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "parameter.display.enabled", havingValue = "true", matchIfMissing = true)
public class ParameterDisplayService {

    private final MessageSource messageSource;
    private final ParameterResourceDiscoveryService resourceDiscoveryService;
    private final ParameterDisplayProperties properties;

    public Map<String, List<String>> initializeParameterDisplayValues() {
        if (!properties.isEnabled()) {
            log.info("Parameter display service is disabled");
            return Collections.emptyMap();
        }

        Map<String, List<String>> parameterMap = new HashMap<>();
        Locale locale = LocaleContextHolder.getLocale();

        // Get all parameter resources
        List<Resource> resources = resourceDiscoveryService.discoverParameterResources();
        
        if (resources.isEmpty()) {
            log.warn("No parameter resources found with pattern: {}", properties.getFullPattern());
            return Collections.emptyMap();
        }

        for (Resource resource : resources) {
            processParameterResource(resource, parameterMap, locale);
        }

        return createSortedUnmodifiableMap(parameterMap);
    }

    private void processParameterResource(Resource resource, Map<String, List<String>> parameterMap, Locale locale) {
        try {
            Properties props = new Properties();
            props.load(resource.getInputStream());

            for (String key : props.stringPropertyNames()) {
                if (resourceDiscoveryService.isParameterKey(key)) {
                    String category = resourceDiscoveryService.extractCategory(key);
                    
                    if (category != null) {
                        String value = messageSource.getMessage(key, null, locale);
                        parameterMap.computeIfAbsent(category, k -> new ArrayList<>()).add(value);
                    }
                }
            }
        } catch (IOException e) {
            log.warn("Could not load parameter properties from resource: {}", resource.getFilename(), e);
        }
    }

    private Map<String, List<String>> createSortedUnmodifiableMap(Map<String, List<String>> parameterMap) {
        parameterMap.values().forEach(Collections::sort);
        Map<String, List<String>> sortedMap = new TreeMap<>(parameterMap);
        return Collections.unmodifiableMap(sortedMap);
    }
}