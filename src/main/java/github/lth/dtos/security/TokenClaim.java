package github.lth.dtos.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import github.lth.enums.TokenType;
import github.lth.enums.UserRole;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenClaim {

    private UUID userId;
    private TokenType tokenType;
    private Set<UserRole> userRoles;
}
