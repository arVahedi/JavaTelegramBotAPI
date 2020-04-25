package telegram.bot.api.requestobject;

import telegram.bot.api.entity.Chat;
import telegram.bot.api.enums.ChatActionEnum;

/**
 * Created by Gladiator on 2/5/2016 AD.
 */
public class RequestSendChatAction {
    private Chat chat;

    private ChatActionEnum action;

    public RequestSendChatAction(){}

    public RequestSendChatAction(Chat chat, ChatActionEnum action){
        this.chat = chat;
        this.action = action;
    }

    public RequestSendChatAction(Object chatId, ChatActionEnum action){
        this.chat = new Chat(chatId);
        this.action = action;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public ChatActionEnum getAction() {
        return action;
    }

    public void setAction(ChatActionEnum action) {
        this.action = action;
    }
}
