package api.exception;

/**
 * Created by Gladiator on 9/3/2016 AD.
 */
public class GetChatException extends RuntimeException {
    public GetChatException() {
        super();
    }

    public GetChatException(String message) {
        super(message);
    }
}
