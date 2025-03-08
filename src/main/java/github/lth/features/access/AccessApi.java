package github.lth.features.access;

import github.lth.dtos.access.LoginForm;
import github.lth.dtos.access.UserAccessResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface AccessApi {

    @GetMapping(value = "/ui/login", produces = MediaType.TEXT_HTML_VALUE)
    Mono<Resource> loginUi();

    @PostMapping(value = "/api/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<UserAccessResponse> login(@RequestBody LoginForm form);

}
