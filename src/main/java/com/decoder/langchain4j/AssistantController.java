package com.decoder.langchain4j;

import dev.langchain4j.service.Result;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {

    private final AssistantAiService assistantAiService;
    private final ChatMessageRepository repository;
    private final ReminderRepository reminderRepository;

    public AssistantController(AssistantAiService assistantAiService, ChatMessageRepository repository,
            ReminderRepository reminderRepository) {
        this.assistantAiService = assistantAiService;
        this.repository = repository;
        this.reminderRepository = reminderRepository;
    }

    @PostMapping()
public String askAssistant(@RequestBody AssistantRequest request) {
    System.out.println("--- Nova Requisição ---");
    System.out.println("Jogo: " + request.game());
    System.out.println("Mensagem: " + request.message());
    System.out.println("ID da Sessão: " + request.sessionId());

    String aiResponse;
    try {
        Result<String> result = assistantAiService.handleRequest(request.game(), request.message());
        aiResponse = result.content();
    } catch (Exception e) {
        aiResponse = "Erro na API: " + e.getMessage();
        System.err.println("ERRO AO CHAMAR GEMINI: " + e.getMessage());
    }

    try {
        ChatMessageEntity chatLog = new ChatMessageEntity(
                request.game(),
                request.message(),
                aiResponse,
                request.sessionId());
        repository.save(chatLog);
        System.out.println("✓ Conversa salva no banco com ID de Sessão: " + request.sessionId());
    } catch (Exception e) {
        System.err.println("❌ ERRO AO SALVAR NO BANCO: " + e.getMessage());
    }

    return aiResponse;
}

    @GetMapping("/history/session")
    public List<ChatMessageEntity> getSessionMessages(@RequestParam String sessionId) {
        return repository.findBySessionIdOrderByTimestampAsc(sessionId);
    }

    @GetMapping("/history")
    public List<ChatMessageEntity> getHistory(@RequestParam String game) {
        return repository.findByGameOrderByTimestampDesc(game);
    }

    @GetMapping("/suggestions")
    public List<String> getAiSuggestions(@RequestParam String game) {
        String raw = assistantAiService.generateSuggestions(game);
        return List.of(raw.split(";"));
    }

    @GetMapping("/reminders")
    public List<ReminderEntity> getReminders(@RequestParam String game) {
        return reminderRepository.findByGameOrderByTimestampDesc(game);
    }
}
