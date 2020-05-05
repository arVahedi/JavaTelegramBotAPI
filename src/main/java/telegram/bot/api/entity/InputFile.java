package telegram.bot.api.entity;

import java.io.Serializable;

/**
 * This object represents the contents of a file to be uploaded.
 * Must be posted using multipart/form-data in the usual way that files are uploaded via the browser.
 */
public class InputFile implements Serializable {

    private String path;

    public InputFile(){}

    public InputFile(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
