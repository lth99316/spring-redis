package github.lth.entities;


import github.lth.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements Persistable<UUID> {

    @Id
    @Column(value = "id")
    private UUID id;

    @Column(value = "display_name")
    private String displayName;

    @Column(value = "user_name")
    private String userName;

    @Column(value = "password")
    private String password;

    @Column(value = "roles")
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();

    @Override
    public boolean isNew() {

        if (this.id == null) {
            this.id = UUID.randomUUID();
            return true;
        }

        return false;
    }
}
