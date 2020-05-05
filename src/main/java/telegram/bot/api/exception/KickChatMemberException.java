package telegram.bot.api.exception;

/**
 * Created by Gladiator on 9/3/2016 AD.
 */
public class KickChatMemberException extends RuntimeException {
    public KickChatMemberException() {
        super();
    }

    public KickChatMemberException(String message) {
        super(message);
    }
}
