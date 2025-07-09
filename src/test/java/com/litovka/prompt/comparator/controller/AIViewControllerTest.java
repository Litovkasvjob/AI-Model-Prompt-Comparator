package com.litovka.prompt.comparator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litovka.prompt.comparator.config.AIConfigurationProperties;
import com.litovka.prompt.comparator.config.AIModelProperties;
import com.litovka.prompt.comparator.dto.PromptDTO;
import com.litovka.prompt.comparator.service.AISelectorService;
import com.litovka.prompt.comparator.service.AIService;
import com.litovka.prompt.comparator.service.ParameterDisplayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Test class for AIViewController.
 * Tests that the controller correctly handles prompt requests and responses.
 */
@ExtendWith(MockitoExtension.class)
public class AIViewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AISelectorService serviceSelector;

    @Mock
    private AIConfigurationProperties aiConfigurationProperties;

    @Mock
    private AIModelProperties aiModelProperties;

    @Mock
    private ParameterDisplayService parameterDisplayService;

    @Mock
    private AIService mockAIService;

    @InjectMocks
    private AIViewController aiViewController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        // Set up mock responses
        Map<String, List<String>> parameterDisplayValues = new HashMap<>();
        parameterDisplayValues.put("parameter1", List.of("value1", "value2"));

        AIConfigurationProperties.Common commonConfig = new AIConfigurationProperties.Common();
        commonConfig.setTemperature(0.7);
        commonConfig.setMaxTokens(1000);
        commonConfig.setTopP(0.9);

        // Use lenient() to avoid "unnecessary stubbing" exceptions
        lenient().when(parameterDisplayService.initializeParameterDisplayValues()).thenReturn(parameterDisplayValues);
        lenient().when(aiModelProperties.getModelsList()).thenReturn(List.of("gpt-3.5-turbo", "gemini-pro"));
        lenient().when(aiConfigurationProperties.getCommon()).thenReturn(commonConfig);

        mockMvc = MockMvcBuilders.standaloneSetup(aiViewController).build();
    }

    @Test
    public void testShowPromptPage() throws Exception {
        mockMvc.perform(get("/ai/prompt"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("modelTypes"))
                .andExpect(model().attributeExists("parameterDisplayValues"))
                .andExpect(model().attributeExists("commonConfig"))
                .andExpect(view().name("ai-prompt"));
    }

    @Test
    public void testGenerateResponse_Success() throws Exception {
        // Mock the service selector and service
        String mockResponse = "This is a test response from AI.";
        when(serviceSelector.getServiceBasedOnModel("gpt-3.5-turbo")).thenReturn(mockAIService);
        when(mockAIService.generateResponse(any(PromptDTO.class), eq("gpt-3.5-turbo"))).thenReturn(mockResponse);

        // Perform the request
        mockMvc.perform(post("/ai/generate")
                        .param("prompt", "Test prompt")
                        .param("models", "gpt-3.5-turbo"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("modelTypes"))
                .andExpect(model().attributeExists("parameterDisplayValues"))
                .andExpect(model().attributeExists("commonConfig"))
                .andExpect(model().attributeExists("promptDTO"))
                .andExpect(view().name("ai-prompt"));
    }

    @Test
    public void testGenerateResponse_MultipleModels() throws Exception {
        // Mock the service selector and services
        String mockResponseGPT = "This is a test response from GPT.";
        String mockResponseGemini = "This is a test response from Gemini.";

        when(serviceSelector.getServiceBasedOnModel("gpt-3.5-turbo")).thenReturn(mockAIService);
        when(serviceSelector.getServiceBasedOnModel("gemini-pro")).thenReturn(mockAIService);

        when(mockAIService.generateResponse(any(PromptDTO.class), eq("gpt-3.5-turbo"))).thenReturn(mockResponseGPT);
        when(mockAIService.generateResponse(any(PromptDTO.class), eq("gemini-pro"))).thenReturn(mockResponseGemini);

        // Perform the request
        mockMvc.perform(post("/ai/generate")
                        .param("prompt", "Test prompt")
                        .param("models", "gpt-3.5-turbo")
                        .param("models", "gemini-pro"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("modelTypes"))
                .andExpect(model().attributeExists("parameterDisplayValues"))
                .andExpect(model().attributeExists("commonConfig"))
                .andExpect(model().attributeExists("promptDTO"))
                .andExpect(view().name("ai-prompt"));
    }

    @Test
    public void testGenerateResponse_EmptyPrompt() throws Exception {
        // Perform the request with empty prompt
        mockMvc.perform(post("/ai/generate")
                        .param("prompt", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("modelTypes"))
                .andExpect(model().attributeExists("parameterDisplayValues"))
                .andExpect(model().attributeExists("commonConfig"))
                .andExpect(model().attributeExists("promptDTO"))
                .andExpect(view().name("ai-prompt"));
    }

    @Test
    public void testGenerateResponse_ServiceException() throws Exception {
        // Mock the service selector and service to throw an exception
        when(serviceSelector.getServiceBasedOnModel("gpt-3.5-turbo")).thenReturn(mockAIService);
        when(mockAIService.generateResponse(any(PromptDTO.class), eq("gpt-3.5-turbo")))
                .thenThrow(new RuntimeException("Service error"));

        // Perform the request
        mockMvc.perform(post("/ai/generate")
                        .param("prompt", "Test prompt")
                        .param("models", "gpt-3.5-turbo"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("modelTypes"))
                .andExpect(model().attributeExists("parameterDisplayValues"))
                .andExpect(model().attributeExists("commonConfig"))
                .andExpect(model().attributeExists("promptDTO"))
                .andExpect(view().name("ai-prompt"));
    }
}
