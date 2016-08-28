package api.entity;

import java.io.Serializable;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public class ChatMember implements Serializable {
    private User user;
    private String status;  //Use ChatMemberStatusEnum class.

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
