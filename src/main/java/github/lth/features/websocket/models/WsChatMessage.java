package github.lth.features.websocket.models;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class WsChatMessage {
    private Integer id;
    private String message;
}
