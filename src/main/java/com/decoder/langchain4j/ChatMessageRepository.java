package com.decoder.langchain4j;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findByGameOrderByTimestampDesc(String game);
    
    List<ChatMessageEntity> findBySessionIdOrderByTimestampAsc(String sessionId);
}