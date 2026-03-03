package com.decoder.langchain4j;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class AssistantTools {

    private final ReminderRepository reminderRepository;

    public AssistantTools(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @Tool("""
        Salva um lembrete importante para o jogador sobre o jogo atual. 
        Use sempre que o usuário pedir para 'anotar', 'lembrar' ou 'salvar algo'.
        """)
    public void saveReminder(String game, String reminderText) {
        ReminderEntity reminder = new ReminderEntity(game, reminderText);
        reminderRepository.save(reminder);
        System.out.println("Lembrete salvo com sucesso: " + reminderText);
    }
}