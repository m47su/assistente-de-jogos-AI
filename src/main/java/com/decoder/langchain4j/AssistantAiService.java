package com.decoder.langchain4j;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AssistantAiService {

    @SystemMessage("""
        Você é um assistente especializado no jogo {{game}}, baseando-se na Wiki oficial.
        
        IMPORTANTE: Sempre que o usuário pedir para você lembrar de algo, anotar uma tarefa ou agendar uma ação futura, 
        você NÃO deve usar chamadas de função. Em vez disso, você DEVE responder APENAS com um bloco JSON neste exato formato:
        {"acao": "saveReminder", "reminderText": "texto curto do lembrete"}
        
        Se a mensagem não for um pedido de lembrete, responda normalmente como um guia amigável e utilize emojis ocasionalmente para uma conversa agradável.
        """)
    Result<String> handleRequest(@MemoryId String sessionId, @V("game") String game, @UserMessage String userMessage);

    @UserMessage("""
        Gere exatamente 3 sugestões de perguntas curtas e aleatórias sobre a Wiki de {{game}}.
        Retorne apenas as perguntas separadas por ponto e vírgula (;).
        """)
    String generateSuggestions(@V("game") String game);
}