package github.lth.entities;


import github.lth.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    @Id
    @Column(value = "id")
    private String id;

    @Column(value = "sender")
    private UUID sender;

    @Column(value = "room_id")
    private UUID groupId;

    @Column(value = "content")
    private String content;

    @Column(value = "type")
    private MessageType type;

//    @Column(value = "status")


}
