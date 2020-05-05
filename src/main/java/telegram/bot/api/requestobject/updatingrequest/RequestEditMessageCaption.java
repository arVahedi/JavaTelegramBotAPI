package telegram.bot.api.requestobject.updatingrequest;

import telegram.bot.api.entity.Chat;
import telegram.bot.api.entity.InlineKeyboardMarkup;
import telegram.bot.api.entity.Message;

import java.io.Serializable;

/**
 * Created by Gladiator on 9/9/2016 AD.
 */
public class RequestEditMessageCaption implements Serializable {
    //region Fields
    private Chat chat;
    private Message message;
    private String inlineMessageId;
    private String caption;
    private InlineKeyboardMarkup replyMarkup;
    //endregion

    //region Constructors
    public RequestEditMessageCaption() {
    }

    public RequestEditMessageCaption(Chat chat) {
        this.chat = chat;
    }

    public RequestEditMessageCaption(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
    }

    public RequestEditMessageCaption(Chat chat, String caption) {
        this(chat);
        this.caption = caption;
    }

    public RequestEditMessageCaption(Chat chat, Message message) {
        this(chat);
        this.message = message;
    }

    public RequestEditMessageCaption(Chat chat, Message message, String caption) {
        this(chat, message);
        this.caption = caption;
    }

    public RequestEditMessageCaption(String inlineMessageId, String caption) {
        this(inlineMessageId);
        this.caption = caption;
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

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public void setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }
    //endregion
}
