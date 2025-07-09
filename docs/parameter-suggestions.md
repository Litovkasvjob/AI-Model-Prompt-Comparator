# Parameter Suggestions Guide

The AI Model Prompt Comparator Service includes a parameter suggestions feature that helps users craft more effective prompts by providing contextual suggestions. This document explains how to use this feature.

## Using Parameter Suggestions

When typing your prompt in the text area, you can access parameter suggestions by typing the `@` symbol followed by a few characters. This will display a dropdown menu with relevant parameter categories and suggestions.

### How It Works

1. Type `@` in the prompt text area
2. A dropdown menu will appear showing parameter categories
3. Hover over a category to see specific parameter suggestions
4. Click on a suggestion to insert it into your prompt

### Available Parameter Categories

The parameter suggestions are organized into categories based on the configuration in the `suggestions` directory. Each category contains a list of relevant parameters that can be included in your prompts.

For example, if you're crafting a prompt about health conditions, typing `@health` might show suggestions like:
- Allergies
- Chronic pain
- Diabetes
- Hypertension

## Customizing Parameter Suggestions

Administrators can customize the available parameter suggestions by modifying the properties files in the `src/main/resources/suggestions/` directory.

### Configuration Files

Parameter suggestions are defined in properties files following the pattern `parameter_*.properties`. For example:

```properties
# src/main/resources/suggestions/parameter_1.properties
parameter_1.category=Health Conditions
parameter_2.suggestions=Allergies
parameter_3.suggestions=Chronic pain
parameter_4.suggestions=Diabetes
parameter_5.suggestions=Hypertension
```

### Adding New Suggestions

To add new parameter suggestions:

1. Create a new properties file in the `src/main/resources/suggestions/` directory following the naming pattern `parameter_X.properties` (where X is a unique number)
2. Define the category and suggestions in the file
3. Restart the application to load the new suggestions

## Best Practices

- Use parameter suggestions to ensure consistency in your prompts
- Combine multiple parameters to create more specific and detailed prompts
- Use the suggestions as a starting point and customize them to fit your specific needs