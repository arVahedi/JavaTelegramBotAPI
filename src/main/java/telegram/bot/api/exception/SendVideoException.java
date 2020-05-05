package telegram.bot.api.exception;

/**
 * Created by Gladiator on 8/31/2016 AD.
 */
public class SendVideoException extends RuntimeException {
    public SendVideoException(){
        super();
    }

    public SendVideoException(String message){
        super(message);
    }
}
