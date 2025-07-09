package com.litovka.prompt.comparator.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up GPT-related services.
 * This class integrates with the application’s configuration properties
 * to provide and configure an {@link OpenAiService} bean, enabling interaction
 * with OpenAI's API.
 */
@Slf4j
@Configuration
public class GPTConfiguration {

    private final AIConfigurationProperties aiConfigurationProperties;

    public GPTConfiguration(AIConfigurationProperties aiConfigurationProperties) {
        this.aiConfigurationProperties = aiConfigurationProperties;
    }

    @Bean
    public OpenAiService openAiService() {
        log.info("Initializing OpenAiService with API key");
        return new OpenAiService(aiConfigurationProperties.getGpt().getApiKey());
    }
}
