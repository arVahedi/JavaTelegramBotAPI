package api.requestobject.updatingrequest;

import api.entity.Chat;
import api.entity.InlineKeyboardMarkup;
import api.entity.Message;

import java.io.Serializable;

/**
 * Created by Gladiator on 9/9/2016 AD.
 */
public class RequestEditMessageReplyMarkup implements Serializable {
    //region Fields
    private Chat chat;
    private Message message;
    private String inlineMessageId;
    private InlineKeyboardMarkup replyMarkup;
    //endregion

    //region Constructors
    public RequestEditMessageReplyMarkup() {
    }

    public RequestEditMessageReplyMarkup(Chat chat) {
        this.chat = chat;
    }

    public RequestEditMessageReplyMarkup(Chat chat, Message message) {
        this(chat);
        this.message = message;
    }

    public RequestEditMessageReplyMarkup(Chat chat, Message message, InlineKeyboardMarkup replyMarkup) {
        this(chat, message);
        this.replyMarkup = replyMarkup;
    }

    public RequestEditMessageReplyMarkup(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
    }

    public RequestEditMessageReplyMarkup(String inlineMessageId, InlineKeyboardMarkup replyMarkup) {
        this(inlineMessageId);
        this.replyMarkup = replyMarkup;
    }
    //endregion

    //region Getter and Setter
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getInlineMessageId() {
        return inlineMessageId;
    }

    public void setInlineMessageId(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public void setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }
    //endregion
}
