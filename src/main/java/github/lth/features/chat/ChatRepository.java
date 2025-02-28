package github.lth.features.chat;

import github.lth.entities.ChatMessageEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ChatRepository extends ReactiveCrudRepository<ChatMessageEntity, UUID> {
}
