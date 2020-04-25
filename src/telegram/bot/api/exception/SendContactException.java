package telegram.bot.api.exception;

/**
 * Created by Gladiator on 8/31/2016 AD.
 */
public class SendContactException extends RuntimeException {
    public SendContactException(){super();}

    public SendContactException(String message){super(message);}
}
