# AI Model Prompt Comparator Service

A web application that allows users to compare responses from different AI models using the same prompt, helping to evaluate and analyze the performance and output quality of various AI language models.

## Overview

The AI Model Prompt Comparator Service is a Spring Boot application that enables users to input a prompt and receive responses from multiple AI models simultaneously. This tool is particularly useful for:

- Comparing the quality of responses from different AI models
- Testing how different models interpret the same prompt
- Evaluating the impact of parameter adjustments on AI responses
- Selecting the most suitable AI model for specific use cases

## Features

- **Multi-Model Comparison**: Compare responses from up to three AI models simultaneously
- **Supported AI Models**:
  - OpenAI GPT models (gpt-4.1, gpt-4.1-mini, gpt-4.1-nano, gpt-4o, gpt-4o-mini, gpt-3.5-turbo)
  - Google Gemini models (gemini-2.5-pro, gemini-2.5-flash, gemini-2.0-flash, gemini-1.5-pro, etc.)
  - Anthropic Claude models (claude-3-opus, claude-3-sonnet, claude-3-haiku, etc.)
- **Parameter Customization**: Adjust AI generation parameters including:
  - Temperature (controls randomness)
  - Max tokens (controls response length)
  - Top-p (controls diversity)
- **Parameter Suggestions**: Access parameter suggestions using '@' symbol in the prompt (see [Parameter Suggestions Guide](docs/parameter-suggestions.md))
- **User-Friendly Interface**: Clean, responsive web interface for easy comparison of AI responses

## Technology Stack

- **Backend**: Java 21 with Spring Boot 3.4.5
- **Frontend**: JSP views with jQuery
- **AI Integrations**:
  - OpenAI API (via openai-gpt3-java)
  - Google Gemini API (via langchain4j)
  - Anthropic Claude API (via langchain4j)
- **Build Tool**: Gradle

## Getting Started

### Prerequisites

- Java 21 or higher
- Gradle 7.0 or higher
- API keys for the AI services you want to use:
  - OpenAI API key
  - Google Gemini API key
  - Anthropic Claude API key

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Litovkasvjob/AI-Model-Prompt-Comparator.git
   cd ai-model-prompt-comparator-service
   ```

2. Configure API keys:
   - Open `src/main/resources/ai.properties`
   - Update the API keys for the services you want to use:
     ```properties
     ai.config.gpt.api-key=your-openai-api-key
     ai.config.gemini.api-key=your-gemini-api-key
     ai.config.claude.api-key=your-claude-api-key
     ```

3. Build the application:
   ```bash
   ./gradlew build
   ```

4. Run the application:
   ```bash
   ./gradlew bootRun
   ```

5. Access the application:
   - Open a web browser and navigate to `http://localhost:8080`
   - The main interface is available at `http://localhost:8080/ai/prompt`

## Configuration

### AI Models Configuration

The application supports multiple AI models from different providers. You can configure which models are available in the `ai.properties` file:

```properties
# Common default AI parameters
ai.config.common.temperature=0.2
ai.config.common.max-tokens=500
ai.config.common.top-p=0.2

# AI Model Definitions
ai.models.models.gpt=gpt-4.1,gpt-4.1-mini,gpt-4.1-nano,gpt-4o,gpt-4o-mini,gpt-3.5-turbo
ai.models.models.gemini=gemini-2.5-pro-preview,gemini-2.5-flash-preview,gemini-2.0-flash,gemini-1.5-pro
ai.models.models.claude=claude-3-opus,claude-3-sonnet,claude-3-haiku
```

For detailed information about AI model configuration, see [AI Model Configuration Guide](docs/ai-model-configuration.md).

### Application Properties

The main application configuration is in `application.properties`:

```properties
spring.application.name=ai-model-prompt-comparator-service
server.port=8080

# JSP Configuration
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

## Usage

1. Navigate to `http://localhost:8080/ai/prompt`
2. Enter your prompt in the text area
   - Use the '@' symbol to access parameter suggestions
3. Select up to three AI models from the dropdown menus
4. Adjust advanced parameters if needed (temperature, max tokens, top-p)
5. Click "Ask AI" to generate responses
6. View and compare the responses from each selected model

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Spring Boot team for the excellent framework
- OpenAI, Google, and Anthropic for their AI models and APIs
- All contributors who have helped shape this project
