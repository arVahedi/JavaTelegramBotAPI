package api.exception;

/**
 * Created by Gladiator on 8/31/2016 AD.
 */
public class SendVenueException extends RuntimeException {
    public SendVenueException() {
        super();
    }

    public SendVenueException(String message) {
        super(message);
    }
}
