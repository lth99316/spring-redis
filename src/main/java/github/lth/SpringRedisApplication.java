package github.lth;

import github.lth.dtos.security.TokenClaim;
import github.lth.entities.UserEntity;
import github.lth.enums.TokenType;
import github.lth.enums.UserRole;
import github.lth.features.security.jwt.JwtSupporter;
import github.lth.features.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@SpringBootApplication
@EnableWebFlux
public class SpringRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtSupporter jwtSupporter;

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        userService.getByUserName("user1")
                .switchIfEmpty(Mono.just(UserEntity.builder()
                        .displayName("user1")
                        .userName("user1")
                        .password(passwordEncoder.encode("123456"))
                        .roles(Set.of(UserRole.USER))
                        .build()))
                .flatMap(userService::save).subscribe();

        userService.getByUserName("user2")
                .switchIfEmpty(Mono.just(UserEntity.builder()
                        .displayName("user2")
                        .userName("user2")
                        .password(passwordEncoder.encode("123456"))
                        .roles(Set.of(UserRole.USER))
                        .build()))
                .flatMap(userService::save).subscribe();

        userService.getAll().map(it -> jwtSupporter.generateToken(TokenClaim.builder()
                        .userId(it.getId())
                        .userRoles(it.getRoles())
                        .tokenType(TokenType.ACCESS_TOKEN).build()))
                .doOnNext(it -> System.out.println(it))
                .subscribe();

    }
}
