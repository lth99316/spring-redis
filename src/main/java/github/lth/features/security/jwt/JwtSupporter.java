package github.lth.features.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.lth.config.ReactiveWebsocketConfig;
import github.lth.config.SecurityConfigProperties;
import github.lth.dtos.security.TokenClaim;
import github.lth.enums.TokenType;
import github.lth.features.exceptions.ForbiddenException;
import github.lth.utils.ECDSAUtils;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.interfaces.ECPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtSupporter {

    private final ObjectMapper objectMapper;
    private final SecurityConfigProperties securityConfigProperties;
    private Map<TokenType, Duration> tokenMap;

    private static final int PUBLIC_KEY = 0;
    private static final int PRIVATE_KEY = 1;
    private final Key[] keypair = new Key[2];

    @PostConstruct
    public void init() {
        tokenMap = securityConfigProperties.getTokenConfigs().stream()
                .collect(Collectors.toMap(k -> k.getType(), v -> v.getDuration()));

        this.keypair[PRIVATE_KEY] = ECDSAUtils.getPrivateKey(securityConfigProperties.getPrivateKey());
        this.keypair[PUBLIC_KEY] = ECDSAUtils.getPublicKey(securityConfigProperties.getPublicKey());
    }

    public String generateToken(final TokenClaim tokenClaim) {
        var issuedAt = Instant.now();
        var expiredAt = issuedAt.plus(Optional.of(tokenMap.get(tokenClaim.getTokenType()))
                .orElseThrow(() -> new UnsupportedOperationException("Unsupported Token Type" + tokenClaim.getTokenType())));

        Map<String, Object> claimMap = objectMapper.convertValue(tokenClaim,
                new TypeReference<Map<String, Object>>() {
                });
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiredAt))
                .issuer("github.lth")
                .claims(claimMap)
                .signWith(keypair[PRIVATE_KEY])
                .compact();
    }

    public TokenClaim verify(final String strToken) {
        Map payLoad = (Map) Jwts.parser()
                .verifyWith((ECPublicKey) keypair[PUBLIC_KEY])
                .build()
                .parse(strToken)
                .getPayload();
        try {
            var strPayload = objectMapper.writeValueAsString(payLoad);
            return objectMapper.readValue(strPayload, TokenClaim.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String extractToken(ServerWebExchange exchange) {
        if (exchange.getRequest().getURI().getPath().equals(ReactiveWebsocketConfig.WEBSOCKET_PATH)) {

            var tokenParam = exchange.getRequest().getQueryParams().get(HttpHeaders.AUTHORIZATION);
           if (tokenParam == null || tokenParam.isEmpty()) {
               throw new ForbiddenException("Token is missing");
           }

           return tokenParam.getFirst();
        }

        var headerToken = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (headerToken == null || headerToken.isEmpty()) {
            throw new ForbiddenException("Token is missing");
        }

        return headerToken.getFirst().replace("Bearer ", "");
    }
}
