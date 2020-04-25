package telegram.bot.api.exception;

/**
 * Created by Gladiator on 9/5/2016 AD.
 */
public class GetChatMemberException extends RuntimeException {
    public GetChatMemberException() {
        super();
    }

    public GetChatMemberException(String message) {
        super(message);
    }
}
