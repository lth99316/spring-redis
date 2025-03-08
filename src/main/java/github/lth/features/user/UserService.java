package github.lth.features.user;

import github.lth.entities.UserEntity;
import github.lth.features.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Flux<UserEntity> getAll() {
        return this.userRepository.findAll();
    }

    public Mono<UserEntity> getByUserName(final String userName) {
        return userRepository.findByUserName(userName);
    }

    @Transactional(rollbackFor = Exception.class)
    public Mono<UserEntity> save(final UserEntity entity) {
        return userRepository.save(entity);
    }
}
