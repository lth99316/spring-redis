package github.lth.features.chat.message;

import github.lth.dtos.chat.message.ChatMessageResponse;
import github.lth.enums.UserRoleConstants;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MessageApi {

    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Mono<String> demo();

    @PreAuthorize("hasRole(" + UserRoleConstants.USER + ")")
    @GetMapping(value = "/chat/group/{groupId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ChatMessageResponse> getAllMessagesByGroupId(@Param("groupId") UUID groupId);


    @PreAuthorize("hasRole(" + UserRoleConstants.USER + ")")
    @GetMapping(value = "/api/chat/message/{targetId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> getAllMessageByTargetId(@PathVariable("userId") UUID tergetId);
}
