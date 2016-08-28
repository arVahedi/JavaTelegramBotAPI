package api.exception;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public class GetMeException extends RuntimeException {
    public GetMeException(){
        super();
    }

    public GetMeException(String message){
        super(message);
    }
}
