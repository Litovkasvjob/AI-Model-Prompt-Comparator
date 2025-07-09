package com.litovka.prompt.comparator;

import com.litovka.prompt.comparator.config.AIConfigurationProperties;
import com.litovka.prompt.comparator.config.AIModelProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * The main application class for running the Prompt Comparator Spring Boot application.
 *
 * The application includes the following configurations:
 * 1. Scans configuration properties from the specified package.
 * 2. Enables binding of configuration properties for AI model and AI configuration settings.
 * 3. Loads external properties from the "ai.properties" file located in the classpath.
 *
 * An entry point to the application is provided through the main method, which initializes
 * the Spring Application context.
 *
 * An associated ServletInitializer class allows deployment in a servlet container.
 */
@SpringBootApplication
@ConfigurationPropertiesScan("com.litovka.prompt.comparator.config")
@EnableConfigurationProperties({AIConfigurationProperties.class, AIModelProperties.class})
@PropertySource("classpath:ai.properties")
public class PromptComparatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromptComparatorApplication.class, args);
    }

}
