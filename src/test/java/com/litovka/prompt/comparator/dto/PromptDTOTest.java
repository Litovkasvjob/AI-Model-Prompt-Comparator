package com.litovka.prompt.comparator.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PromptDTO.
 * Tests the functionality of the DTO including constructors, getters, setters, and other methods.
 */
public class PromptDTOTest {

    @Test
    public void testNoArgsConstructor() {
        PromptDTO promptDTO = new PromptDTO();
        
        assertNull(promptDTO.getPrompt());
        assertNull(promptDTO.getAnswers());
        assertNull(promptDTO.getModels());
        assertNull(promptDTO.getTemperature());
        assertNull(promptDTO.getMaxTokens());
        assertNull(promptDTO.getTopP());
    }

    @Test
    public void testAllArgsConstructor() {
        String prompt = "Test prompt";
        List<String> answers = Arrays.asList("Answer 1", "Answer 2");
        List<String> models = Arrays.asList("gpt-3.5-turbo", "gemini-pro");
        Double temperature = 0.7;
        Integer maxTokens = 1000;
        Double topP = 0.9;

        PromptDTO promptDTO = new PromptDTO(prompt, answers, models, temperature, maxTokens, topP);
        
        assertEquals(prompt, promptDTO.getPrompt());
        assertEquals(answers, promptDTO.getAnswers());
        assertEquals(models, promptDTO.getModels());
        assertEquals(temperature, promptDTO.getTemperature());
        assertEquals(maxTokens, promptDTO.getMaxTokens());
        assertEquals(topP, promptDTO.getTopP());
    }

    @Test
    public void testSettersAndGetters() {
        PromptDTO promptDTO = new PromptDTO();
        
        String prompt = "Test prompt";
        List<String> answers = Arrays.asList("Answer 1", "Answer 2");
        List<String> models = Arrays.asList("gpt-3.5-turbo", "gemini-pro");
        Double temperature = 0.7;
        Integer maxTokens = 1000;
        Double topP = 0.9;
        
        promptDTO.setPrompt(prompt);
        promptDTO.setAnswers(answers);
        promptDTO.setModels(models);
        promptDTO.setTemperature(temperature);
        promptDTO.setMaxTokens(maxTokens);
        promptDTO.setTopP(topP);
        
        assertEquals(prompt, promptDTO.getPrompt());
        assertEquals(answers, promptDTO.getAnswers());
        assertEquals(models, promptDTO.getModels());
        assertEquals(temperature, promptDTO.getTemperature());
        assertEquals(maxTokens, promptDTO.getMaxTokens());
        assertEquals(topP, promptDTO.getTopP());
    }

    @Test
    public void testEqualsAndHashCode() {
        String prompt = "Test prompt";
        List<String> answers = Arrays.asList("Answer 1", "Answer 2");
        List<String> models = Arrays.asList("gpt-3.5-turbo", "gemini-pro");
        Double temperature = 0.7;
        Integer maxTokens = 1000;
        Double topP = 0.9;

        PromptDTO promptDTO1 = new PromptDTO(prompt, answers, models, temperature, maxTokens, topP);
        PromptDTO promptDTO2 = new PromptDTO(prompt, answers, models, temperature, maxTokens, topP);
        PromptDTO promptDTO3 = new PromptDTO("Different prompt", answers, models, temperature, maxTokens, topP);
        
        // Test equals
        assertEquals(promptDTO1, promptDTO2);
        assertNotEquals(promptDTO1, promptDTO3);
        
        // Test hashCode
        assertEquals(promptDTO1.hashCode(), promptDTO2.hashCode());
        assertNotEquals(promptDTO1.hashCode(), promptDTO3.hashCode());
    }

    @Test
    public void testToString() {
        PromptDTO promptDTO = new PromptDTO();
        promptDTO.setPrompt("Test prompt");
        
        String toString = promptDTO.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("prompt=Test prompt"));
    }
}