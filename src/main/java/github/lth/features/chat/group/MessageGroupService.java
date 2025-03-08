package github.lth.features.chat.group;

import github.lth.entities.MessageGroupEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageGroupService {

    private final MessageGroupRepository messageGroupRepository;

    public Mono<MessageGroupEntity> getOneByOwnerAndTargetId(final UUID ownerId, final UUID targetId) {
        return  messageGroupRepository.findOneByOwnerIdAndTargetId(ownerId, targetId);
    }
}
