package api.exception;

/**
 * Created by Gladiator on 9/3/2016 AD.
 */
public class DownloadFileException extends RuntimeException {
    public DownloadFileException() {
        super();
    }

    public DownloadFileException(String message) {
        super(message);
    }
}
