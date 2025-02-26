package github.lth.entities;


import github.lth.enums.GroupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {

    @Id
    @Column(value = "id")
    private UUID id;

    @Column(value = "title")
    private String title;

    @Column(value = "status")
    private GroupStatus status;

    @Column(value = "is_single")
    private Boolean isSingle;
}
