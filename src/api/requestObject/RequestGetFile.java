package api.requestObject;

import api.entity.File;

/**
 * Created by Gladiator on 2/5/2016 AD.
 */
public class RequestGetFile {
    private File file;

    public RequestGetFile(){}

    public RequestGetFile(File file){
        this.file = file;
    }

    public RequestGetFile(String fileId){
        this.file = new File(fileId);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
