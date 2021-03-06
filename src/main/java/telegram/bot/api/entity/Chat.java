package telegram.bot.api.entity;

import java.io.Serializable;

public class Chat implements Serializable {
    //region Fields
    private long id;
    // TODO: 9/4/2016 AD use ChatTypeEnum class for type attribute
    private String type;    // User ChatTypeEnum class.
    private String title;
    private String username;
    private String first_name;
    private String last_name;
    //endregion

    //region Constructors
    public Chat() {
    }

    public Chat(long id) {
        this.id = id;
    }

    public Chat(int id) {
        this.id = id;
    }
    //endregion

    //region Getter and Setter
    public Chat(String username) {
        this.username = username;
    }

    public Chat(Object id) {
        if (id instanceof Integer) {
            this.id = (int) id;
        } else if (id instanceof String) {
            if (((String) id).startsWith("@")) {
                this.username = String.valueOf(id);
            } else {
                this.username = "@" + String.valueOf(id);
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getChatId() {
        if (this.id != 0) {
            return String.valueOf(this.id);
        } else if (this.username != null) {
            return this.username;
        }

        return null;
    }

    public boolean isValid() {
        return this.id != 0 || this.username != null;
    }
    //endregion
}
