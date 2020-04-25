package telegram.bot.api.exception;

/**
 * Created by Gladiator on 2/5/2016 AD.
 */
public class GetUpdateException extends RuntimeException {

    public GetUpdateException() {
        super();
    }

    public GetUpdateException(String message) {
        super(message);
    }
}
