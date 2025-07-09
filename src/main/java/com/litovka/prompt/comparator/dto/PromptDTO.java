package com.litovka.prompt.comparator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for transferring prompt-related data.
 * Used to encapsulate data for prompt generation and response handling.
 * Typically utilized in scenarios involving language models or AI systems
 * where prompt customization and configuration is required.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptDTO {

    private String prompt;
    private List<String> answers;
    private List<String> models;
    private Double temperature;
    private Integer maxTokens;
    private Double topP;

}
