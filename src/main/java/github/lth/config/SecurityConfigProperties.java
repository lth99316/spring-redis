package github.lth.config;


import github.lth.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@PropertySource(value = "${app.security}")
public class SecurityConfigProperties {

    private List<TokenConfig> tokenConfigs;

    private String publicKey;
    private String privateKey;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenConfig {
        private TokenType name;
        private Duration duration;
    }
}
