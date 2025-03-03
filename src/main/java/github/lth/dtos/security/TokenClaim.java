package github.lth.dtos.security;

import github.lth.enums.TokenType;
import github.lth.enums.UserRole;
import lombok.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenClaim {

    private UUID userId;
    private TokenType tokenType;
    private List<UserRole> userRoles;
}
