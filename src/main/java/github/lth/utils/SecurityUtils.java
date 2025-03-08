package github.lth.utils;

import github.lth.features.security.user.JwtUserDetail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    public static Mono<UUID> getUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(it -> (JwtUserDetail) it.getAuthentication().getPrincipal())
                .map(it -> it.getTokenClaim().getUserId());
    }
}
