package github.lth.features.chat.message;

import github.lth.dtos.chat.message.ChatMessageResponse;
import github.lth.entities.MessageEntity;
import github.lth.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController implements MessageApi {

    private final MessageService messageService;

    @Override
    public Mono<String> demo() {
        return Mono.just("Ok");
    }

    @Override
    public Flux<ChatMessageResponse> getAllMessagesByGroupId(UUID groupId) {
        return null;
    }

    @Override
    public Flux<String> getAllMessageByTargetId(UUID targetId) {
        return SecurityUtils.getUserId()
                .map(it -> messageService.getAllByUserId(it, targetId))
                .flatMapMany(it -> it)
                .map(MessageEntity::getContent);
    }
}
