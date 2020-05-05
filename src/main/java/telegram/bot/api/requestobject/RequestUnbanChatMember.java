package telegram.bot.api.requestobject;

import telegram.bot.api.entity.Chat;
import telegram.bot.api.entity.User;

import java.io.Serializable;

/**
 * Created by Gladiator on 9/3/2016 AD.
 */
public class RequestUnbanChatMember implements Serializable {
    //region Fields
    private Chat chat;
    private User user;
    //endregion

    //region Constructors
    public RequestUnbanChatMember() {
    }

    public RequestUnbanChatMember(Chat chat, User user) {
        this.chat = chat;
        this.user = user;
    }

    public RequestUnbanChatMember(Object chatId, int userId) {
        this.chat = new Chat(chatId);
        this.user = new User(userId);
    }

    public RequestUnbanChatMember(Chat chat) {
        this.chat = chat;
    }

    public RequestUnbanChatMember(User user) {
        this.user = user;
    }

    public RequestUnbanChatMember(Object chatId) {
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
