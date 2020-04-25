package telegram.bot.api.exception;

/**
 * Created by Gladiator on 2/5/2016 AD.
 */
public class SendChatActionException extends RuntimeException {

    public SendChatActionException() {
        super();
    }

    public SendChatActionException(String message) {
        super(message);
    }
}
