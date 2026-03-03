package com.decoder.langchain4j;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReminderRepository extends JpaRepository<ReminderEntity, Long> {
    List<ReminderEntity> findByGameOrderByTimestampDesc(String game);
}