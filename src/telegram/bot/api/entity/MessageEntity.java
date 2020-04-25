package telegram.bot.api.entity;

import java.io.Serializable;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public class MessageEntity implements Serializable {
    private String type;
    private int offset;
    private int length;
    private String url;
    private User user;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
