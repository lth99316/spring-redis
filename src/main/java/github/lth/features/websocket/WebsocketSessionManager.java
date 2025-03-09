package github.lth.features.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class WebsocketSessionManager {

    Map<UUID, Set<String>> userSessionIdMap = new ConcurrentHashMap<>();
    Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    public void registerSession(final UUID userId, final WebSocketSession session) {

        var sessionId = session.getId();
        var currentSession = sessionMap.putIfAbsent(session.getId(), session);

        if (currentSession == null ) {

            userSessionIdMap.putIfAbsent(userId, new HashSet<>());
            userSessionIdMap.computeIfPresent(userId, (k,v) -> {
                v.add(sessionId);
                return v;
            });
        }
    }

    public void removeSession(final UUID userId, final String sessionId) {
        var currentSession = sessionMap.remove(sessionId);

        if (currentSession != null) {
            currentSession.close(CloseStatus.NORMAL);
            userSessionIdMap.computeIfPresent(userId, (k, v) -> {
                v.remove(sessionId);
                return v;
            });
        }
    }

    public void sendMessage() {
        // Todo: impl
    }

}
