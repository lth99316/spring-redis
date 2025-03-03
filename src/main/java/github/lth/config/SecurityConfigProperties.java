package github.lth.config;


import github.lth.enums.TokenType;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityConfigProperties {

    private List<TokenConfig> tokenConfigs;

    private List<String> skipApi;

    private String publicKey;
    private String privateKey;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TokenConfig {
        private TokenType type;
        private Duration duration;
    }
}
