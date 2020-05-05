package telegram.bot.api.entity;

import java.io.Serializable;

public class File implements Serializable {
    //region Fields
    private String file_id;
    private int file_size;
    private String file_path;
    //endregion

    //region Constructors
    public File() {
    }

    public File(String fileId) {
        this.file_id = fileId;
    }
    //endregion

    //region Getter and Setter
    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
    //endregion
}
