package github.lth.dtos.access;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccessResponse {

    private String accessToken;
    private String refreshToken;
}
