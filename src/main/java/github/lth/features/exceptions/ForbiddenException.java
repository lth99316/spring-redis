package github.lth.features.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.service.annotation.HttpExchange;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends AbstractAppException {

    public ForbiddenException(String message) {
        super(message);
    }
}
