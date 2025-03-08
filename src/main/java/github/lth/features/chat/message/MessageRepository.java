package github.lth.features.chat.message;

import github.lth.entities.MessageEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface MessageRepository extends ReactiveCrudRepository<MessageEntity, UUID> {

    Flux<MessageEntity> findAllByRoomId(UUID roomId);
}
