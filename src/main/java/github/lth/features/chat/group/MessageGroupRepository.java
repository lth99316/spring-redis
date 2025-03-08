package github.lth.features.chat.group;

import github.lth.entities.MessageGroupEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MessageGroupRepository extends ReactiveCrudRepository<MessageGroupEntity, UUID> {

    Mono<MessageGroupEntity> findOneByOwnerIdAndTargetId(UUID ownerId, UUID targetId);
}
