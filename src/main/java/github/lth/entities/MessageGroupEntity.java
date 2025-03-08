package github.lth.entities;


import github.lth.enums.ChatGroupStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "message_group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageGroupEntity {

    @Id
    @Column(value = "id")
    private UUID id;

    @Column(value = "title")
    private String title;

    @Column(value = "status")
    private ChatGroupStatus status;

    @Column(value = "owner")
    private UUID ownerId;

    // Todo: refactor

    @Column(value = "is_single")
    private Boolean isSingle;

    @Column(value = "code") // for group
    private String code;

    @Column(value = "targetId") // for single
    private UUID targetId;


}
