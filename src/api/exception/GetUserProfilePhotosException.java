package api.exception;

/**
 * Created by Gladiator on 2/5/2016 AD.
 */
public class GetUserProfilePhotosException extends RuntimeException{

    public GetUserProfilePhotosException(){super();}

    public GetUserProfilePhotosException(String message){
        super(message);
    }
}
