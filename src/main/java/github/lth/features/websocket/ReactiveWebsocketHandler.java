package github.lth.features.websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import github.lth.dtos.chat.message.ChatMessageResponse;
import github.lth.features.security.user.JwtUserDetail;
import github.lth.features.websocket.models.WsChatMessage;
import github.lth.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveWebsocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper;
    private final WebsocketSessionManager websocketSessionManager;
    private final Sinks.Many<WsChatMessage> messageSink = Sinks.many().multicast().directBestEffort();

    Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession session) {


        sessionMap.put(session.getId(), session);

//        var securityContent = ReactiveSecurityContextHolder.getContext()
//                .map(it -> (JwtUserDetail) it.getAuthentication().getPrincipal());
//
//        securityContent.subscribe(it -> {
//            websocketSessionManager.registerSession(it.getTokenClaim().getUserId(), session);
//        });
//
//        var fluxMessage = messageSink.asFlux()
//                .flatMap(it -> Mono.fromCallable(() -> objectMapper.writeValueAsString(it)))
//                .map(session::textMessage);
//
//        session.send(fluxMessage);


//        session.receive().subscribe(it -> System.out.println(it.getPayloadAsText()));
//        return session.send(Flux.fromStream(Stream.generate(() -> getData()))
//                        .delayElements(Duration.ofSeconds(5))
//                        .map(session::textMessage))
//                .and(session.receive()
//                        .map(WebSocketMessage::getPayloadAsText)
//                        .log());


        return session.receive()
                .flatMap(message -> {
                    String payload = message.getPayloadAsText();

                    // Todo: map to object

                    return Flux.fromIterable(sessionMap.values())
                            .filter(s -> !s.getId().equals(session.getId()))
                            .flatMap(s -> {
                                    return s.send(Mono.just(s.textMessage(payload)));
                            })
                            .then(); // Complete this inner broadcast
                })
                .doOnError(err -> System.err.println("Error: " + err.getMessage()))
                .then();
    }


}
