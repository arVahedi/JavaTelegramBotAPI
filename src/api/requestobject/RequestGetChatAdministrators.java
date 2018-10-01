package api.requestobject;

import api.entity.Chat;

import java.io.Serializable;

/**
 * Created by Gladiator on 9/4/2016 AD.
 */
public class RequestGetChatAdministrators implements Serializable {
    //region Fields
    private Chat chat;
    //endregion

    //region Constructors
    public RequestGetChatAdministrators() {
    }

    public RequestGetChatAdministrators(Chat chat) {
        this.chat = chat;
    }

    public RequestGetChatAdministrators(Object chatId) {
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
    //endregion
}
