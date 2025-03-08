package github.lth.features.exceptions.handler;

import github.lth.features.exceptions.AbstractAppException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected Mono<ResponseEntity<Object>> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return handleException(ex);
    }

    @ExceptionHandler(RuntimeException.class)
    protected Mono<ResponseEntity<Object>> handleRuntimeException(final RuntimeException exception, final ServerWebExchange exchange) {
        return handleException(exception);
    }

    private Mono<ResponseEntity<Object>> handleException(Exception ex) {
        final var status = ex.getClass().getAnnotation(ResponseStatus.class).value();

        return Mono.just(ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .message(castToAppException(ex).getMessage())
                        .timestamp(Instant.now())
                        .build()));
    }

    public AbstractAppException castToAppException(Exception exception) {
        if (exception instanceof AbstractAppException) {
            log.error(exception.getMessage());

            return (AbstractAppException) exception;
        } else {
            return new AbstractAppException(exception.getMessage());
        }
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorResponse {
        private String message;
        private Instant timestamp;
    }
}
