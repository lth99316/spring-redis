package github.lth.features.dashboard;

import github.lth.enums.UserRoleConstants;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

public interface DashboardApi {

    @GetMapping(value = "/ui/home", produces = MediaType.TEXT_HTML_VALUE)
    Mono<Resource> dashboard();
}
