package com.litovka.prompt.comparator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for AI-related settings within the application.
 * It supports configuring general common properties shared across models,
 * as well as specific properties for different AI-powered services or models
 * including Gemini, GPT, and Claude.
 */
@Data
@ConfigurationProperties(prefix = "ai.config")
public class AIConfigurationProperties {

    private Common common = new Common();
    private Gemini gemini = new Gemini();
    private GPT gpt = new GPT();
    private Claude claude = new Claude();


    @Data
    public static class Common {
        /**
         * The temperature parameter used to control the randomness of AI model outputs.
         */
        private Double temperature;

        /**
         * Represents the maximum allowable number of tokens that can be processed.
         */
        private Integer maxTokens;

        /**
         * The top-p value (also known as nucleus sampling).
         */
        private Double topP;
    }


    @Data
    public static class Gemini {
        private String apiKey;
        private String model;
    }

    @Data
    public static class GPT {
        private String apiKey;
        private String model;
    }

    @Data
    public static class Claude {
        private String apiKey;
        private String model;
    }
}

