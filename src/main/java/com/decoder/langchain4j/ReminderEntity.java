package com.decoder.langchain4j;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ReminderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String game;
    private String reminderText;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ReminderEntity() {}
    public ReminderEntity(String game, String reminderText) {
        this.game = game;
        this.reminderText = reminderText;
    }
    // Getters e Setters
    public String getReminderText() { return reminderText; }
    public String getGame() { return game; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
}