package api.exception;

/**
 * Created by Gladiator on 9/3/2016 AD.
 */
public class LeaveChatException extends RuntimeException {
    public LeaveChatException() {
        super();
    }

    public LeaveChatException(String message) {
        super(message);
    }
}
