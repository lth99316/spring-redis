package github.lth.features.user;

import github.lth.dtos.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public Flux<UserResponse> getAll() {
        return userService.getAll()
                .map(it -> UserResponse.builder()
                        .id(it.getId())
                        .displayName(it.getDisplayName())
                        .build());
    }
}
