package github.lth.features.chat.message;

import github.lth.entities.MessageEntity;
import github.lth.features.chat.group.MessageGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageGroupService messageGroupService;

    public Flux<MessageEntity> getAllByUserId(final UUID userId, final UUID targetId) {
        return messageGroupService.getOneByOwnerAndTargetId(userId, targetId)
                .map(it -> messageRepository.findAllByRoomId(it.getId()))
                .flatMapMany(it -> it);
    }

}
