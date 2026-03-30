package com.decoder.langchain4j;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssistantConfig {

    @Value("${gemini.api-key}")
    private String geminiApiKey;

    @Value("${gemini.model}")
    private String geminiModel;

    @Bean
    public GoogleAiGeminiChatModel googleAiGeminiChatModel() {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(geminiApiKey)
                .modelName(geminiModel)
                .logRequestsAndResponses(true)
                .build();
    }
        @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return sessionId -> MessageWindowChatMemory.withMaxMessages(20);
    }

    @Bean
    public AssistantAiService assistant(
            GoogleAiGeminiChatModel model, 
            AssistantTools assistantTools, 
            ChatMemoryProvider chatMemoryProvider) { 
        
        return AiServices.builder(AssistantAiService.class)
                .chatModel(model)
                .tools(assistantTools)
                .chatMemoryProvider(chatMemoryProvider) 
                .build();
    }
}

