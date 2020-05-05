package telegram.bot.api.exception;

public class ForwardMessageException extends RuntimeException {

    public ForwardMessageException() {
        super();
    }

    public ForwardMessageException(String message) {
        super(message);
    }
}
