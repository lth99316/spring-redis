package github.lth.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String value;

    UserRole(final String value) {
        this.value = value;
    }
}
