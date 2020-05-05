package telegram.bot.api.exception.updatingexception;

/**
 * Created by gladiator on 9/7/16.
 */
public class EditMessageTextException extends RuntimeException {
    public EditMessageTextException() {
        super();
    }

    public EditMessageTextException(String message) {
        super(message);
    }
}
