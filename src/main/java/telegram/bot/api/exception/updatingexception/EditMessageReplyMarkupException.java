package telegram.bot.api.exception.updatingexception;

/**
 * Created by Gladiator on 9/9/2016 AD.
 */
public class EditMessageReplyMarkupException extends RuntimeException {
    public EditMessageReplyMarkupException() {
        super();
    }

    public EditMessageReplyMarkupException(String message) {
        super(message);
    }
}
