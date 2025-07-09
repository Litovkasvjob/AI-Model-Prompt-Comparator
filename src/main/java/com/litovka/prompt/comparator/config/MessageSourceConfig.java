package com.litovka.prompt.comparator.config;

import com.litovka.prompt.comparator.service.ParameterResourceDiscoveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.List;

/**
 * Configuration class for setting up a {@link MessageSource} bean to handle
 * internationalization and localization by resolving parameter files specified
 * through the application's configuration. This class allows dynamic discovery
 * and integration of parameter files at runtime.
 *
 * The {@link MessageSource} is configured with the following properties:
 * - Basenames: Discovered dynamically from the parameter files.
 * - Default Encoding: Specified in {@link ParameterDisplayProperties}.
 * - Cache Seconds: Specifies the cache duration for reloading message properties.
 *
 * This configuration is enabled if the property `parameter.display.enabled` is
 * set to `true` or is not explicitly defined.
 *
 * Dependencies:
 * - {@link ParameterResourceDiscoveryService}: Service responsible for discovering
 *   parameter files dynamically.
 * - {@link ParameterDisplayProperties}: The configuration properties bean that
 *   supplies the necessary values for discovering and configuring parameter files.
 *
 * An instance of this class enables scanning and loading of parameter
 * resources stated under the given pattern in the application's configuration.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ParameterDisplayProperties.class})
public class MessageSourceConfig {

    private final ParameterResourceDiscoveryService resourceDiscoveryService;
    private final ParameterDisplayProperties properties;

    @Bean
    @ConditionalOnProperty(name = "parameter.display.enabled", havingValue = "true", matchIfMissing = true)
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        
        // Dynamically discover all parameter files
        List<String> basenames = resourceDiscoveryService.discoverParameterBasenames();
        
        if (basenames.isEmpty()) {
            log.warn("No parameter files discovered with pattern: {}", properties.getFullPattern());
        } else {
            log.info("Configured MessageSource with {} parameter files", basenames.size());
        }
        
        messageSource.setBasenames(basenames.toArray(new String[0]));
        messageSource.setDefaultEncoding(properties.getEncoding());
        messageSource.setCacheSeconds(properties.getCacheSeconds());
        
        return messageSource;
    }
}