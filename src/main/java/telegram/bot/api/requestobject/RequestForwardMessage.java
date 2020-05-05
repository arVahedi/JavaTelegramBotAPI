package telegram.bot.api.requestobject;

import telegram.bot.api.entity.Chat;
import telegram.bot.api.entity.Message;

/**
 * Created by Gladiator on 1/23/2016 AD.
 */
public class RequestForwardMessage {
    //region Required Fields
    private Message message;
    private Chat chat;
    //endregion

    //region Optional Fields
    private boolean disableNotification = false;
    //endregion

    //region Constructors
    public RequestForwardMessage() {
    }

    public RequestForwardMessage(Chat chat, Message message) {
        this.chat = chat;
        this.message = message;
    }

    public RequestForwardMessage(Object chatId, Object fromChatId, int messageId) {
        this.chat = new Chat(chatId);
        this.message = new Message();
        this.message.setChat(new Chat(fromChatId));
        this.message.setMessageId(messageId);
    }
    //endregion

    //region Getter and Setter
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

    public boolean isDisableNotification() {
        return disableNotification;
    }

    public void setDisableNotification(boolean disableNotification) {
        this.disableNotification = disableNotification;
    }
    //endregion
}
