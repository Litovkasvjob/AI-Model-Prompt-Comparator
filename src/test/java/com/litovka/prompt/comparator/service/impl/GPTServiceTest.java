package com.litovka.prompt.comparator.service.impl;

import com.litovka.prompt.comparator.dto.PromptDTO;
import com.litovka.prompt.comparator.exception.GPTException;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for GPTService.
 * Tests the GPT-specific implementation of the AIService interface.
 */
@ExtendWith(MockitoExtension.class)
public class GPTServiceTest {

    @Mock
    private OpenAiService openAiService;

    private GPTService gptService;
    private PromptDTO promptDTO;
    private String model;

    @BeforeEach
    public void setup() {
        gptService = new GPTService(openAiService);
        
        promptDTO = new PromptDTO();
        promptDTO.setPrompt("Test prompt");
        promptDTO.setTemperature(0.7);
        promptDTO.setMaxTokens(1000);
        promptDTO.setTopP(0.9);
        
        model = "gpt-3.5-turbo";
    }

    @Test
    public void testGetModelType() {
        assertEquals("GPT", gptService.getModelType());
    }

    @Test
    public void testCreateException() {
        String errorMessage = "Test error";
        Exception cause = new RuntimeException("Test cause");
        
        RuntimeException exception = gptService.createException(errorMessage, cause);
        
        assertTrue(exception instanceof GPTException);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testCallModelApi_Success() {
        // Arrange
        String expectedResponse = "This is a test response from GPT";
        
        // Create a mock response
        ChatMessage responseMessage = new ChatMessage();
        responseMessage.setContent(expectedResponse);
        
        ChatCompletionChoice choice = new ChatCompletionChoice();
        choice.setMessage(responseMessage);
        
        ChatCompletionResult result = new ChatCompletionResult();
        result.setChoices(Collections.singletonList(choice));
        
        when(openAiService.createChatCompletion(any(ChatCompletionRequest.class))).thenReturn(result);
        
        // Act
        String response = gptService.generateResponse(promptDTO, model);
        
        // Assert
        assertEquals(expectedResponse, response);
        
        // Verify the request was built correctly
        ArgumentCaptor<ChatCompletionRequest> requestCaptor = ArgumentCaptor.forClass(ChatCompletionRequest.class);
        verify(openAiService).createChatCompletion(requestCaptor.capture());
        
        ChatCompletionRequest capturedRequest = requestCaptor.getValue();
        assertEquals(model, capturedRequest.getModel());
        assertEquals(promptDTO.getTemperature(), capturedRequest.getTemperature());
        assertEquals(promptDTO.getMaxTokens(), capturedRequest.getMaxTokens());
        assertEquals(promptDTO.getTopP(), capturedRequest.getTopP());
        
        List<ChatMessage> messages = capturedRequest.getMessages();
        assertEquals(1, messages.size());
        assertEquals("user", messages.get(0).getRole());
        assertEquals(promptDTO.getPrompt(), messages.get(0).getContent());
    }

    @Test
    public void testCallModelApi_Exception() {
        // Arrange
        RuntimeException apiException = new RuntimeException("API error");
        when(openAiService.createChatCompletion(any(ChatCompletionRequest.class))).thenThrow(apiException);
        
        // Act & Assert
        Exception exception = assertThrows(GPTException.class, () -> {
            gptService.generateResponse(promptDTO, model);
        });
        
        // Verify the exception was created correctly
        assertTrue(exception.getMessage().contains("GPT"));
        assertEquals(apiException, exception.getCause());
    }
}