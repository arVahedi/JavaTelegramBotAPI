package api.requestObject;

import api.entity.Chat;
import api.entity.ReplyKeyboardMarkup;

/**
 * Created by Gladiator on 1/23/2016 AD.
 */
public class RequestSendMessage {
    private Chat chat;
    private String text;
    private String parseMode;
    private boolean disableWebPagePreview = false;
    private int replyToMessageId;
    private ReplyKeyboardMarkup replyMarkup;

    public RequestSendMessage() {
    }

    public RequestSendMessage(Chat chat, String text) {
        this.chat = chat;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParseMode() {
        return parseMode;
    }

    public void setParseMode(String parseMode) {
        this.parseMode = parseMode;
    }

    public boolean isDisableWebPagePreview() {
        return disableWebPagePreview;
    }

    public void setDisableWebPagePreview(boolean disableWebPagePreview) {
        this.disableWebPagePreview = disableWebPagePreview;
    }

    public boolean getDisableWebPagePreview(){
        return this.disableWebPagePreview;
    }

    public int getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    public ReplyKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public void setReplyMarkup(ReplyKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
