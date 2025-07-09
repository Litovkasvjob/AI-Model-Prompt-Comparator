# AI Model Configuration Guide

This guide explains how to configure and use different AI models in the AI Model Prompt Comparator Service.

## Supported AI Models

The application supports three major AI model providers:

1. **OpenAI GPT Models**
   - gpt-4.1
   - gpt-4.1-mini
   - gpt-4.1-nano
   - gpt-4o
   - gpt-4o-mini
   - gpt-3.5-turbo

2. **Google Gemini Models**
   - gemini-2.5-pro-preview-03-25
   - gemini-2.5-flash-preview-04-17
   - gemini-2.0-flash-001
   - gemini-2.0-flash-lite-001
   - gemini-1.5-pro-001
   - gemini-1.5-flash-001

3. **Anthropic Claude Models**
   - claude-3-7-sonnet-20250219
   - claude-3-5-sonnet-20240620
   - claude-3-opus-20240229
   - claude-3-sonnet-20240229
   - claude-3-haiku-20240307

## Configuration

### API Keys

To use the AI models, you need to obtain API keys from each provider and configure them in the `src/main/resources/ai.properties` file:

```properties
# OpenAI properties
ai.config.gpt.api-key=your-openai-api-key

# Gemini properties
ai.config.gemini.api-key=your-gemini-api-key

# Anthropic properties
ai.config.claude.api-key=your-claude-api-key
```

### Model Lists

You can customize which models are available in the application by modifying the model lists in the `ai.properties` file:

```properties
# AI Model Definitions
ai.models.models.gpt=gpt-4.1,gpt-4.1-mini,gpt-4.1-nano,gpt-4o,gpt-4o-mini,gpt-3.5-turbo
ai.models.models.gemini=gemini-2.5-pro-preview-03-25,gemini-2.5-flash-preview-04-17,gemini-2.0-flash-001,gemini-1.5-pro-001
ai.models.models.claude=claude-3-opus-20240229,claude-3-sonnet-20240229,claude-3-haiku-20240307
```

### Default Parameters

You can set default parameters for all AI models in the `ai.properties` file:

```properties
# Common default AI parameters
ai.config.common.temperature=0.2
ai.config.common.max-tokens=500
ai.config.common.top-p=0.2
```

## How Model Selection Works

The application uses the `AISelectorService` to determine which AI service implementation to use based on the model name:

- If the model name contains "gemini" (case insensitive), the `GeminiService` is used
- If the model name contains "claude" (case insensitive), the `ClaudeService` is used
- For all other models (including GPT models), the `GPTService` is used as the default

## Adding New Models

To add support for new models:

1. Update the model lists in `ai.properties` to include the new model names
2. If the new models are from an existing provider (OpenAI, Google, or Anthropic), no code changes are needed
3. If adding models from a new provider:
   - Create a new service implementation that extends `BaseAIService`
   - Update the `AISelectorService` to recognize and route requests to the new service

## Model Parameters

### Temperature

Controls the randomness of the AI's responses:
- Lower values (e.g., 0.2) produce more focused, deterministic responses
- Higher values (e.g., 0.8) produce more random, creative responses
- Valid range: 0 to 2

### Max Tokens

Controls the maximum length of the AI's response:
- Higher values allow for longer responses
- Lower values force the AI to be more concise
- Valid range: 1 to 8192 (varies by model)

### Top-P

Controls diversity by limiting the cumulative probability of token selection:
- Lower values (e.g., 0.5) make the output more focused
- Higher values (e.g., 0.9) make the output more diverse
- Valid range: 0 to 1

## Troubleshooting

### API Key Issues

If you encounter errors related to API keys:
- Verify that the API keys are correctly entered in the `ai.properties` file
- Check that the API keys have not expired
- Ensure that the API keys have the necessary permissions

### Model Availability

If a model is not available:
- Check that the model is correctly listed in the `ai.properties` file
- Verify that the model is still supported by the provider
- Ensure that your API key has access to the specific model

### Response Generation Errors

If you encounter errors during response generation:
- Check the application logs for detailed error messages
- Verify that the parameters (temperature, max tokens, top-p) are within valid ranges
- Try reducing the complexity of your prompt or the length of the requested response