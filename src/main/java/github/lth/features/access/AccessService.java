package github.lth.features.access;

import github.lth.dtos.access.LoginForm;
import github.lth.dtos.access.UserAccessResponse;
import github.lth.dtos.security.TokenClaim;
import github.lth.entities.UserEntity;
import github.lth.enums.TokenType;
import github.lth.enums.UserRole;
import github.lth.features.exceptions.BadRequestException;
import github.lth.features.security.jwt.JwtSupporter;
import github.lth.features.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccessService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtSupporter jwtSupporter;

    public Mono<UserAccessResponse> login(final LoginForm form) {
        return userService.getByUserName(form.getUsername())
                .filter(it -> passwordEncoder.matches(form.getPassword(), it.getPassword()))
                .switchIfEmpty(Mono.error(new BadRequestException("Username or password is wrong")))
                .map(it -> UserAccessResponse.builder()
                        .accessToken(jwtSupporter.generateToken(TokenClaim.builder()
                                .userId(it.getId())
                                .userRoles(it.getRoles())
                                .tokenType(TokenType.ACCESS_TOKEN).build()))
                        .refreshToken(jwtSupporter.generateToken(TokenClaim.builder()
                                .userId(it.getId())
                                .userRoles(it.getRoles())
                                .tokenType(TokenType.REFRESH_TOKEN)
                                .build()))
                        .build());
    }

}
