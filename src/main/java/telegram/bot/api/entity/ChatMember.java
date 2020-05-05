package telegram.bot.api.entity;

import telegram.bot.api.enums.ChatMemberStatusEnum;

import java.io.Serializable;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public class ChatMember implements Serializable {
    //region Fields
    private User user;
    private ChatMemberStatusEnum status;
    //endregion

    //region Constructors
    public ChatMember() {
    }

    public ChatMember(User user) {
        this.user = user;
    }

    public ChatMember(int userId) {
        this.user = new User(userId);
    }

    public ChatMember(User user, ChatMemberStatusEnum status) {
        this(user);
        this.status = status;
    }

    public ChatMember(int userId, ChatMemberStatusEnum status) {
        this(userId);
        this.status = status;
    }
    //endregion

    //region Getter and Setter
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatMemberStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ChatMemberStatusEnum status) {
        this.status = status;
    }
    //endregion
}
