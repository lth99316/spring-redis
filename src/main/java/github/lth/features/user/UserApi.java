package github.lth.features.user;

import github.lth.dtos.user.UserResponse;
import github.lth.enums.UserRoleConstants;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

public interface UserApi {

    @PreAuthorize("hasRole(" + UserRoleConstants.USER + ")")
    @GetMapping(value = "/api/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<UserResponse> getAll();
}
