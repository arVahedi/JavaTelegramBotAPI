package api.requestobject;

import api.entity.File;

import java.io.Serializable;

/**
 * Created by Gladiator on 2/5/2016 AD.
 */
public class RequestGetFile implements Serializable {
    //region Fields
    private File file;
    //endregion

    //region Constructors
    public RequestGetFile() {
    }

    public RequestGetFile(File file) {
        this.file = file;
    }

    public RequestGetFile(String fileId) {
        this.file = new File(fileId);
    }
    //endregion

    //region Getter and Setter
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    //endregion
}
