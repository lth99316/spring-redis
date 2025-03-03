package github.lth.config;

import github.lth.features.security.jwt.JwtFilter;
import github.lth.features.security.jwt.JwtSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtSupporter jwtSupporter;

    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {

        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authorizeExchange(authorizeExchangeSpec -> {
                    authorizeExchangeSpec.pathMatchers("/public/**").permitAll()
                            .anyExchange().authenticated();
                });

        http.addFilterBefore(new JwtFilter(jwtSupporter), SecurityWebFiltersOrder.HTTP_BASIC);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // https://stackoverflow.com/q/56056404
    @Bean
    public WebSessionManager webSessionManager() {
        return exchange -> Mono.empty();
    }
}
