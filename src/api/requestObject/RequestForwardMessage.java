package api.requestObject;

import api.entity.Chat;
import api.entity.Message;

/**
 * Created by Gladiator on 1/23/2016 AD.
 */
public class RequestForwardMessage {
    private Message message;
    private Chat chat;

    public RequestForwardMessage(){}

    public RequestForwardMessage(Message message, Chat chat){
        this.message = message;
        this.chat = chat;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
