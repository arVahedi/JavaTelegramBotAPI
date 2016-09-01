package api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserProfilePhoto implements Serializable {
    //region Fields
    private int total_count;
    private List<List<PhotoSize>> photos = new ArrayList<List<PhotoSize>>();
    //endregion

    //region Getter and Setter
    public int getTotalCount() {
        return total_count;
    }

    public void setTotalCount(int totalCount) {
        this.total_count = totalCount;
    }

    public List<List<PhotoSize>> getPhotos() {
        return photos;
    }

    public void setPhotos(List<List<PhotoSize>> photos) {
        this.photos = photos;
    }
    //endregion

    public void addPhotoList(List<PhotoSize> photoList){
        this.photos.add(photoList);
    }
}
