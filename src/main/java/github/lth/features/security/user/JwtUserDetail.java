package github.lth.features.security.user;

import github.lth.dtos.security.TokenClaim;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class JwtUserDetail implements UserDetails {

    private final TokenClaim tokenClaim;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return tokenClaim.getUserRoles().stream()
                .map(it -> (GrantedAuthority) it::getValue)
                .toList();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
