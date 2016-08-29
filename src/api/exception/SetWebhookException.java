package api.exception;

/**
 * Created by Gladiator on 2/26/2016 AD.
 */
public class SetWebhookException extends RuntimeException {
    public SetWebhookException(){super();}

    public SetWebhookException(String message){super(message);}
}