package com.decoder.langchain4j;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        try {
            Result<String> result = assistantAiService.handleRequest(
                request.sessionId(), 
                request.game(), 
                request.message()
            );
            
            String aiResponse = result.content();

            // Intercepta a resposta para ver se é um JSON de lembrete
            if (aiResponse.contains("\"acao\"") && aiResponse.contains("saveReminder")) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    // Limpa formatações Markdown (```json) que a IA possa adicionar
                    String jsonStr = aiResponse.replaceAll("```json", "").replaceAll("```", "").trim();
                    JsonNode node = mapper.readTree(jsonStr);
                    String reminderText = node.get("reminderText").asText();
                    
                    // Salva o lembrete manualmente no banco
                    ReminderEntity reminder = new ReminderEntity(request.game(), reminderText);
                    reminderRepository.save(reminder);
                    System.out.println("Lembrete salvo manualmente: " + reminderText);
                    
                    // Sobrescreve a resposta da IA com a mensagem de sucesso
                    aiResponse = "Prontinho! Lembrete salvo: " + reminderText + " 📝";
                } catch (Exception e) {
                    System.err.println("Erro ao converter JSON do lembrete: " + e.getMessage());
                }
            }

            ChatMessageEntity chatLog = new ChatMessageEntity(
                    request.game(),
                    request.message(),
                    aiResponse,
                    request.sessionId());
            repository.save(chatLog);
            
            return aiResponse;
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
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
