package com.litovka.prompt.comparator.service;

import com.litovka.prompt.comparator.dto.PromptDTO;

public interface AIService {

    String generateResponse(PromptDTO promptDTO, String model);
}
