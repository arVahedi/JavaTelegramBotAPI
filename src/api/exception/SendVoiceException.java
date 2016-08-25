package api.exception;

/**
 * Created by Gladiator on 2/25/2016 AD.
 */
public class SendVoiceException extends RuntimeException {
    public SendVoiceException() {
        super();
    }

    public SendVoiceException(String message) {
        super(message);
    }
}
