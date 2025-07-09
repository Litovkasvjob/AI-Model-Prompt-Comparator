package com.litovka.prompt.comparator.service.impl;

import com.litovka.prompt.comparator.dto.PromptDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class for BaseAIService.
 * Tests the template method pattern and error handling in the abstract base class.
 */
@ExtendWith(MockitoExtension.class)
public class BaseAIServiceTest {

    /**
     * Concrete implementation of BaseAIService for testing purposes.
     */
    private static class TestAIService extends BaseAIService {
        private final String modelType;
        private final RuntimeException exceptionToThrow;
        private final String responseToReturn;
        private boolean callModelApiCalled = false;

        public TestAIService(String modelType, String responseToReturn, RuntimeException exceptionToThrow) {
            this.modelType = modelType;
            this.responseToReturn = responseToReturn;
            this.exceptionToThrow = exceptionToThrow;
        }

        @Override
        protected String getModelType() {
            return modelType;
        }

        @Override
        protected String callModelApi(PromptDTO promptDTO, String model) {
            callModelApiCalled = true;
            if (exceptionToThrow != null) {
                throw exceptionToThrow;
            }
            return responseToReturn;
        }

        @Override
        protected RuntimeException createException(String message, Exception cause) {
            return new RuntimeException(message, cause);
        }

        public boolean isCallModelApiCalled() {
            return callModelApiCalled;
        }
    }

    private PromptDTO promptDTO;
    private String model;

    @BeforeEach
    public void setup() {
        promptDTO = new PromptDTO();
        promptDTO.setPrompt("Test prompt");
        model = "test-model";
    }

    @Test
    public void testGenerateResponse_Success() {
        // Arrange
        String expectedResponse = "Test response";
        TestAIService service = new TestAIService("TestAI", expectedResponse, null);

        // Act
        String response = service.generateResponse(promptDTO, model);

        // Assert
        assertEquals(expectedResponse, response);
        assertTrue(service.isCallModelApiCalled());
    }

    @Test
    public void testGenerateResponse_Exception() {
        // Arrange
        RuntimeException expectedException = new RuntimeException("Test exception");
        TestAIService service = new TestAIService("TestAI", null, expectedException);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.generateResponse(promptDTO, model);
        });

        // Verify the exception message contains the model type
        assertTrue(exception.getMessage().contains("TestAI"));
        assertTrue(service.isCallModelApiCalled());
    }

    @Test
    public void testGenerateResponse_WithRealImplementation() {
        // This test uses a spy to verify the abstract methods are called correctly
        BaseAIService serviceSpy = spy(new BaseAIService() {
            @Override
            protected String getModelType() {
                return "SpyAI";
            }

            @Override
            protected String callModelApi(PromptDTO promptDTO, String model) {
                return "Spy response";
            }

            @Override
            protected RuntimeException createException(String message, Exception cause) {
                return new RuntimeException(message, cause);
            }
        });

        // Act
        String response = serviceSpy.generateResponse(promptDTO, model);

        // Assert
        assertEquals("Spy response", response);
        verify(serviceSpy, atLeastOnce()).getModelType();
        verify(serviceSpy).callModelApi(promptDTO, model);
    }

    @Test
    public void testGenerateResponse_ExceptionHandling() {
        // This test verifies that exceptions are properly handled and transformed
        Exception originalException = new Exception("Original exception");

        BaseAIService serviceSpy = spy(new BaseAIService() {
            @Override
            protected String getModelType() {
                return "SpyAI";
            }

            @Override
            protected String callModelApi(PromptDTO promptDTO, String model) {
                throw new RuntimeException("API call failed", originalException);
            }

            @Override
            protected RuntimeException createException(String message, Exception cause) {
                return new RuntimeException("Transformed: " + message, cause);
            }
        });

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            serviceSpy.generateResponse(promptDTO, model);
        });

        // Verify exception handling
        assertTrue(exception.getMessage().contains("Transformed"));
        assertTrue(exception.getMessage().contains("SpyAI"));
        verify(serviceSpy).createException(anyString(), any(Exception.class));
    }
}
