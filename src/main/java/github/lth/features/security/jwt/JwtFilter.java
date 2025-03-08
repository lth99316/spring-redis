package github.lth.features.security.jwt;

import github.lth.config.SecurityConfigProperties;
import github.lth.dtos.security.TokenClaim;
import github.lth.features.security.matcher.SkipRequestMatcher;
import github.lth.features.security.user.JwtUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter implements WebFilter {

    private final JwtSupporter jwtSupporter;
    private final SkipRequestMatcher skipRequestMatcher;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return shouldNotFilter(exchange)
                .flatMap(it -> {
                    if (!it.isMatch()) {
                        var tokenClaim = jwtSupporter.verify(jwtSupporter.extractToken(exchange));
                        setAuthentication(tokenClaim);
                    }
                    return chain.filter(exchange);
                });
    }

    private void setAuthentication(final TokenClaim tokenClaim) {
        var userDetail = new JwtUserDetail(tokenClaim);
        var authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());

        ReactiveSecurityContextHolder.withAuthentication(authentication);
    }

    public Mono<ServerWebExchangeMatcher.MatchResult> shouldNotFilter(ServerWebExchange exchange) {
        return skipRequestMatcher.getRequestMatchers().matches(exchange);
    }

}
