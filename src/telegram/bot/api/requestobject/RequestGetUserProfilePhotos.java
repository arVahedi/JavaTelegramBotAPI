package telegram.bot.api.requestobject;

import telegram.bot.api.entity.User;

/**
 * Created by Gladiator on 2/5/2016 AD.
 */
public class RequestGetUserProfilePhotos {
    //region Fields
    private User user;
    private int offset;
    private int limit;
    //endregion

    //region Constructor
    public RequestGetUserProfilePhotos() {
    }

    public RequestGetUserProfilePhotos(User user) {
        this.user = user;
    }

    public RequestGetUserProfilePhotos(int userId) {
        this.user = new User(userId);
    }
    //endregion

    //region Getter and Setter
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    //endregion
}
