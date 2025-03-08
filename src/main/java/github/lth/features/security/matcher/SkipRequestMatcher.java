package github.lth.features.security.matcher;

import github.lth.config.SecurityConfigProperties;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Component
@RequiredArgsConstructor
public class SkipRequestMatcher {

    private final SecurityConfigProperties securityConfigProperties;

    private OrServerWebExchangeMatcher requestMatchers;

    @PostConstruct
    public void init() {
        this.requestMatchers = new OrServerWebExchangeMatcher(securityConfigProperties.getSkipApi()
                .stream().map(PathPatternParserServerWebExchangeMatcher::new)
                .collect(Collectors.toList()));

    }
}
