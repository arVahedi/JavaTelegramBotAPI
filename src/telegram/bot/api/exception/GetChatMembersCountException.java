package telegram.bot.api.exception;

/**
 * Created by Gladiator on 9/4/2016 AD.
 */
public class GetChatMembersCountException extends RuntimeException {
    public GetChatMembersCountException() {
        super();
    }

    public GetChatMembersCountException(String message) {
        super(message);
    }
}
