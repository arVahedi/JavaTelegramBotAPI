package api.exception;

public class AnswerInlineQueryException extends RuntimeException {
    public AnswerInlineQueryException() {
        super();
    }

    public AnswerInlineQueryException(String message) {
        super(message);
    }
}
