package api.entity;

import java.io.Serializable;

public class Audio implements Serializable {
    private String file_id;
    private int duration;
    private String performer;
    private String title;
    private String mime_type;
    private int file_size;

    public Audio() {
    }

    public Audio(String file_id) {
        this.file_id = file_id;
    }

    public Audio(String file_id, int duration, String performer, String title, String mime_type, int file_size){
        this.file_id = file_id;
        this.duration = duration;
        this.performer = performer;
        this.title = title;
        this.mime_type = mime_type;
        this.file_size = file_size;
    }

    public String getFileId() {
        return file_id;
    }

    public void setFileId(String fileId) {
        this.file_id = fileId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimeType() {
        return mime_type;
    }

    public void setMimeType(String mimeType) {
        this.mime_type = mimeType;
    }

    public int getFileSize() {
        return file_size;
    }

    public void setFileSize(int fileSize) {
        this.file_size = fileSize;
    }

}
