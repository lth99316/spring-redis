package github.lth.features.access;

import github.lth.dtos.access.LoginForm;
import github.lth.dtos.access.UserAccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class AccessController implements AccessApi {

    @Value("classpath:/static/login.html")
    private Resource loginHtml;

    private final AccessService accessService;

    @Override
    public Mono<Resource> loginUi() {
        return Mono.just(loginHtml);
    }

    @Override
    public Mono<UserAccessResponse> login(final LoginForm form) {

        System.out.println(form);

        return accessService.login(form);
    }
}
