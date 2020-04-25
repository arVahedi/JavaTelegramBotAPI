package api.requestobject;

import java.io.Serializable;

/**
 * Created by Gladiator on 9/3/2016 AD.
 */
public class RequestDownloadFile implements Serializable {
    //region Fields
    private String uri;     // Url of file on Telegram server. for get this uri use getFile() method.
    private String path;    // Use this path for save file.
    private String name;    // Use this name for downloaded file.

    private static final String API_DOWNLOAD_FILE_URL = "https://api.telegram.org/file/bot/<file_path>";
    //endregion

    //region Constructors
    public RequestDownloadFile() {
    }

    public RequestDownloadFile(String uri) {
        this.uri = uri;
    }

    public RequestDownloadFile(String uri, String path) {
        this(uri);
        this.path = path;
    }

    public RequestDownloadFile(String uri, String path, String name) {
        this(uri, path);
        this.name = name;
    }
    //endregion

    //region Getter and Setter
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion
}
