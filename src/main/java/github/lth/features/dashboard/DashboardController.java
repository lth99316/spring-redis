package github.lth.features.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DashboardController implements DashboardApi {

    @Value("classpath:/static/index.html")
    private Resource indexHtml;

    @Override
    public Mono<Resource> dashboard() {
        return Mono.just(indexHtml);
    }
}
