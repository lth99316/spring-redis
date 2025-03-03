package github.lth.features.security.jwt;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import github.lth.config.SecurityConfigProperties;
import github.lth.dtos.security.TokenClaim;
import github.lth.enums.TokenType;
import github.lth.enums.UserRole;
import github.lth.utils.ECDSAUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtFilterTest {

    private JwtSupporter jwtSupporter;
    private SecurityConfigProperties securityConfigProperties;

    @BeforeEach
    void init() {
        var strTokenPair = ECDSAUtils.genToken();
        this.securityConfigProperties = new SecurityConfigProperties();

        securityConfigProperties.setPrivateKey(strTokenPair.getFirst());
        securityConfigProperties.setPublicKey(strTokenPair.getLast());

        this.jwtSupporter = new JwtSupporter(JsonMapper.builder().addModule(new JavaTimeModule()).build(),
                this.securityConfigProperties);
    }

    @Test
    void handleJwtTokenTest() {

        var tokenClaimRequest = TokenClaim.builder()
                .tokenType(TokenType.ACCESS_TOKEN)
                .userId(UUID.randomUUID())
                .userRoles(List.of(UserRole.USER))
                .build();

        var strToken = jwtSupporter.generateToken(tokenClaimRequest);
        var tokenClaim = jwtSupporter.verify(strToken);

        Assertions.assertEquals(tokenClaimRequest, tokenClaim);
    }

    @Test
    void t() {
        // Todo: check redis not contain user session
        // redisTemplate.keys("spring:session:sessions:*");
    }


}