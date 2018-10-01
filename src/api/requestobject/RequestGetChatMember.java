package api.requestobject;

import api.entity.Chat;
import api.entity.User;

import java.io.Serializable;

/**
 * Created by Gladiator on 9/5/2016 AD.
 */
public class RequestGetChatMember implements Serializable {
    //region Fields
    private Chat chat;
    private User user;
    //endregion

    //region Constructors
    public RequestGetChatMember() {
    }

    public RequestGetChatMember(Chat chat, User user) {
        this.chat = chat;
        this.user = user;
    }

    public RequestGetChatMember(Object chatId, int userId) {
        this.chat = new Chat(chatId);
        this.user = new User(userId);
    }

    public RequestGetChatMember(Chat chat) {
        this.chat = chat;
    }

    public RequestGetChatMember(User user) {
        this.user = user;
    }

    public RequestGetChatMember(Object chatId) {
        this.chat = new Chat(chatId);
    }
    //endregion

    //region Getter and Setter
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //endregion
}
