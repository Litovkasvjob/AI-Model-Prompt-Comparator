package com.litovka.prompt.comparator.service;

import com.litovka.prompt.comparator.service.impl.ClaudeService;
import com.litovka.prompt.comparator.service.impl.GPTService;
import com.litovka.prompt.comparator.service.impl.GeminiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test class for AISelectorService.
 * Tests the service selection logic based on model names.
 */
@ExtendWith(MockitoExtension.class)
public class AISelectorServiceTest {

    @Mock
    private GPTService gptService;

    @Mock
    private GeminiService geminiService;

    @Mock
    private ClaudeService claudeService;

    private AISelectorService selectorService;

    @BeforeEach
    public void setup() {
        selectorService = new AISelectorService(gptService, geminiService, claudeService);
    }

    @Test
    public void testGetServiceBasedOnModel_Gemini() {
        // Test with exact match
        AIService service = selectorService.getServiceBasedOnModel("gemini-pro");
        assertSame(geminiService, service);

        // Test with case insensitivity
        service = selectorService.getServiceBasedOnModel("GEMINI-pro");
        assertSame(geminiService, service);

        // Test with substring
        service = selectorService.getServiceBasedOnModel("google-gemini");
        assertSame(geminiService, service);
    }

    @Test
    public void testGetServiceBasedOnModel_Claude() {
        // Test with exact match
        AIService service = selectorService.getServiceBasedOnModel("claude-3-opus");
        assertSame(claudeService, service);

        // Test with case insensitivity
        service = selectorService.getServiceBasedOnModel("CLAUDE-3-sonnet");
        assertSame(claudeService, service);

        // Test with substring
        service = selectorService.getServiceBasedOnModel("anthropic-claude");
        assertSame(claudeService, service);
    }

    @Test
    public void testGetServiceBasedOnModel_GPT() {
        // Test with GPT model
        AIService service = selectorService.getServiceBasedOnModel("gpt-3.5-turbo");
        assertSame(gptService, service);

        // Test with another model that doesn't match Gemini or Claude
        service = selectorService.getServiceBasedOnModel("llama-2");
        assertSame(gptService, service);

        // Test with null model name
        service = selectorService.getServiceBasedOnModel(null);
        assertSame(gptService, service);

        // Test with empty model name
        service = selectorService.getServiceBasedOnModel("");
        assertSame(gptService, service);
    }
}