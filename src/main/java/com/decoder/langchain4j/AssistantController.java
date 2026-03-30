package com.decoder.langchain4j;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.service.Result;

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

    String originalMessage = request.message();
    String message = originalMessage.toLowerCase();

    boolean isReminderRequest =
            message.contains("me lembre") ||
            message.contains("me lembrar") ||
            message.contains("lembre de") ||
            message.contains("lembrar de") ||
            message.contains("anote") ||
            message.contains("anotar") ||
            message.contains("salva isso") ||
            message.contains("salvar isso") ||
            message.contains("guarde isso") ||
            message.contains("guardar isso");

    if (isReminderRequest) {
    try {
        ReminderEntity reminder = new ReminderEntity(request.game(), originalMessage);
        reminderRepository.save(reminder);
        System.out.println("Lembrete salvo com sucesso: " + originalMessage);

        String aiResponse = "Pronto! Salvei seu lembrete com sucesso.";

        ChatMessageEntity chatLog = new ChatMessageEntity(
                request.game(),
                request.message(),
                aiResponse,
                request.sessionId());
        repository.save(chatLog);
        System.out.println("✓ Conversa salva no banco com ID de Sessão: " + request.sessionId());

        return aiResponse;
    } catch (Exception e) {
        System.err.println("❌ ERRO AO SALVAR LEMBRETE: " + e.getMessage());
        return "Tentei salvar seu lembrete, mas ocorreu um erro.";
    }
}

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
