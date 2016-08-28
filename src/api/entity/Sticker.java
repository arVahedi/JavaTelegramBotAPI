package api.entity;

import java.io.Serializable;

public class Sticker implements Serializable {
    private String file_id;
    private int width;
    private int height;
    private PhotoSize thumb;
    private int file_size;

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

    public PhotoSize getThumb() {
        return thumb;
    }

    public void setThumb(PhotoSize thumb) {
        this.thumb = thumb;
    }

    public int getFileSize() {
        return file_size;
    }

    public void setFileSize(int fileSize) {
        this.file_size = fileSize;
    }
}
