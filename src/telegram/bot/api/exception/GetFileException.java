package telegram.bot.api.exception;

/**
 * Created by Gladiator on 2/5/2016 AD.
 */
public class GetFileException extends RuntimeException {
    public GetFileException() {
        super();
    }

    public GetFileException(String message) {
        super(message);
    }
}
