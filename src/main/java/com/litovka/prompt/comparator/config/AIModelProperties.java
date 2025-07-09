package com.litovka.prompt.comparator.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AIModelProperties is a configuration properties class that allows the storage
 * and management of configuration related to AI models within the application.
 * It specifically works with a mapping of model names and their associated attributes,
 * which can be configured through the application's configuration mechanism.
 *
 * This class is annotated with Spring's @ConfigurationProperties, enabling it to bind
 * externalized configuration properties defined in a properties or YAML file with a
 * prefix of "ai.models".
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "ai.models")
public class AIModelProperties {

    private Map<String, List<String>> models = new ConcurrentHashMap<>();


    public List<String> getModelsList() {
        List<String> modelsList = models.values().stream()
                .flatMap(value -> value.stream()
                        .flatMap(s -> Arrays.stream(s.split(",")))
                        .filter(model -> !model.isEmpty()))
                .sorted()
                .toList();
        return modelsList;
    }


}

