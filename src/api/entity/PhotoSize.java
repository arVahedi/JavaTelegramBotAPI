package api.entity;

import java.io.Serializable;

public class PhotoSize implements Serializable {
    //region Fields
    private String file_id;
    private int width;
    private int height;
    private int file_size;
    //endregion

    //region Constructors
    public PhotoSize() {
    }

    public PhotoSize(String file_id) {
        this.file_id = file_id;
    }

    public PhotoSize(String file_id, int width, int height, int file_size) {
        this(file_id);
        this.width = width;
        this.height = height;
        this.file_size = file_size;
    }

    //region Getter and Setter
    public String getFileId() {
        return file_id;
    }

    public void setFileId(String fileId) {
        this.file_id = fileId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFileSize() {
        return file_size;
    }

    public void setFileSize(int fileSize) {
        this.file_size = fileSize;
    }
    //endregion
}
