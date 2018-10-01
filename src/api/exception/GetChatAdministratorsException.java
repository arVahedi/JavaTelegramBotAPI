package api.exception;

/**
 * Created by Gladiator on 9/4/2016 AD.
 */
public class GetChatAdministratorsException extends RuntimeException {
    public GetChatAdministratorsException() {
        super();
    }

    public GetChatAdministratorsException(String message) {
        super(message);
    }
}
