package camp.model;

public class ScoreException extends Exception {
    // 생성자 1: 기본 생성자
    public ScoreException() {
        super();
    }

    // 생성자 2: 메시지를 인자로 받는 생성자
    public ScoreException(String message) {
        super(message);
    }

    // 생성자 3: 메시지와 원인을 인자로 받는 생성자
    public ScoreException(String message, Throwable cause) {
        super(message, cause);
    }

    // 생성자 4: 원인을 인자로 받는 생성자
    public ScoreException(Throwable cause) {
        super(cause);
    }
}