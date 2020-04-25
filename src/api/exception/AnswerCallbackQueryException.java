package api.exception;

/**
 * Created by Gladiator on 9/5/2016 AD.
 */
public class AnswerCallbackQueryException extends RuntimeException {
    public AnswerCallbackQueryException() {
        super();
    }

    public AnswerCallbackQueryException(String message) {
        super(message);
    }
}
