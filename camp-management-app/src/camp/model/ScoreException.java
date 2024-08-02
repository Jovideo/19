package camp.model;

public class ScoreException extends  RuntimeException{

    // 기본 생성자
    public ScoreException() {
        super();
    }

    // 예외 메시지를 인수로 받는 생성자
    public ScoreException(String s) {
        super(s);
    }

    // 예외 메시지와 원인(cause)를 인수로 받는 생성자
    public ScoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
