package camp.model;

public class ScoreException extends RuntimeException {
    public ScoreException() {
        super();
    }

    public ScoreException(String message) {
        super(message);
    }

    public ScoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScoreException(Throwable cause) {
        super(cause);
    }
}
