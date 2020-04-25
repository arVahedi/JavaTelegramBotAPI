package telegram.bot.api.requestobject;

import telegram.bot.api.entity.Chat;

import java.io.Serializable;

/**
 * Created by Gladiator on 9/3/2016 AD.
 */
public class RequestLeaveChat implements Serializable {
    //region Fields
    private Chat chat;
    //endregion

    //region Constructors
    public RequestLeaveChat(){}

    public RequestLeaveChat(Chat chat){
        this.chat = chat;
    }

    public RequestLeaveChat(Object chatId){
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
