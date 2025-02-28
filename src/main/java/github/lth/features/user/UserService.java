package github.lth.features.user;

import github.lth.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserEntity> getByUserName(final String userName) {
        return userRepository.findByUserName(userName);
    }
}
