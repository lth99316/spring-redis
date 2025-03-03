package github.lth.features.exceptions;

public abstract class AbstractAppException extends RuntimeException {

    private final String message;

    protected AbstractAppException(String message) {
        // Todo: refine
        super();
        this.message = message;
    }
}
