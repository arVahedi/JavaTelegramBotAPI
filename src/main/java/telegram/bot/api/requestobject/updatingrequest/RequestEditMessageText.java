package telegram.bot.api.requestobject.updatingrequest;

import telegram.bot.api.entity.Chat;
import telegram.bot.api.entity.InlineKeyboardMarkup;
import telegram.bot.api.entity.Message;
import telegram.bot.api.enums.ParseModeEnum;

import java.io.Serializable;

/**
 * Created by gladiator on 9/7/16.
 */
public class RequestEditMessageText implements Serializable {
    //region Fields
    private Chat chat;
    private Message message;
    private String inlineMessageId;
    private String text;
    private ParseModeEnum parseMode;
    private boolean disableWebPagePreview = false;
    private InlineKeyboardMarkup replyMarkup;
    //endregion

    //region Constructors
    public RequestEditMessageText() {
    }

    public RequestEditMessageText(Chat chat) {
        this.chat = chat;
    }

    public RequestEditMessageText(String inlineMessageId) {
        this.inlineMessageId = inlineMessageId;
    }

    public RequestEditMessageText(Chat chat, String text) {
        this(chat);
        this.text = text;
    }

    public RequestEditMessageText(Chat chat, Message message) {
        this(chat);
        this.message = message;
    }

    public RequestEditMessageText(Chat chat, Message message, String text) {
        this(chat, message);
        this.text = text;
    }

    public RequestEditMessageText(String inlineMessageId, String text) {
        this(inlineMessageId);
        this.text = text;
    }
    //endregion

    //region Getter and Setter
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ParseModeEnum getParseMode() {
        return parseMode;
    }

    public void setParseMode(ParseModeEnum parseMode) {
        this.parseMode = parseMode;
    }

    public boolean isDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public void setDisableWebPagePreview(boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
    }

    public InlineKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public void setReplyMarkup(InlineKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
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
    //endregion
}
