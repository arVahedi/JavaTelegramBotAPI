package api.exception;

/**
 * Created by Gladiator on 9/3/2016 AD.
 */
public class UnbanChatMemberException extends RuntimeException {
    public UnbanChatMemberException() {
        super();
    }

    public UnbanChatMemberException(String message) {
        super(message);
    }
}
