package com.decoder.langchain4j;

import org.springframework.stereotype.Component;


@Component
public class AssistantTools {

    private final ReminderRepository reminderRepository;

    public AssistantTools(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public void saveReminder(String game, String reminderText) {
        ReminderEntity reminder = new ReminderEntity(game, reminderText);
        reminderRepository.save(reminder);
        System.out.println("Lembrete salvo com sucesso: " + reminderText);
    }
}