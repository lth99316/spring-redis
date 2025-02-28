package github.lth.features.user;

import github.lth.entities.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {

    Mono<UserEntity> findByUserName(final String userName);
}
