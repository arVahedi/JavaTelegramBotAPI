package telegram.bot.api.exception;

/**
 * Created by Gladiator on 1/23/2016 AD.
 */
public class SendMessageException extends RuntimeException {

    public SendMessageException() {
        super();
    }

    public SendMessageException(String message) {
        super(message);
    }

}
