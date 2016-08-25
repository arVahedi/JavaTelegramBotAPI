package api.requestObject;

import api.entity.Chat;
import api.entity.ForceReply;
import api.entity.ReplyKeyboardHide;
import api.entity.ReplyKeyboardMarkup;
import com.sun.istack.internal.NotNull;

/**
 * Created by Gladiator on 1/23/2016 AD.
 */
public class RequestSendMessage {
    private Chat chat;
    private String text;
    private String parseMode;
    private boolean disableWebPagePreview = false;
    private int replyToMessageId;

    //Access these field by setReplyMarkup() and getReplyMarkup() functions
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ReplyKeyboardHide replyKeyboardHide;
    private ForceReply forceReply;

    public RequestSendMessage() {
    }

    public RequestSendMessage(Chat chat, String text) {
        this.chat = chat;
        this.text = text;
    }

    public RequestSendMessage(Object chatId, String text) {
        this.chat = new Chat(chatId);
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

    public boolean getDisableWebPagePreview() {
        return this.disableWebPagePreview;
    }

    public int getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    public void setReplyMarkup(Object replyMarkup) {
        if (replyMarkup instanceof ReplyKeyboardMarkup) {
            this.replyKeyboardMarkup = (ReplyKeyboardMarkup) replyMarkup;
        } else if (replyMarkup instanceof ReplyKeyboardHide) {
            this.replyKeyboardHide = (ReplyKeyboardHide) replyMarkup;
        } else if (replyMarkup instanceof ForceReply) {
            this.forceReply = (ForceReply) replyMarkup;
        }
    }

    public Object getReplyMarkup(){
        if (this.replyKeyboardMarkup != null){
            return this.replyKeyboardMarkup;
        }if (this.replyKeyboardHide != null){
            return this.replyKeyboardHide;
        }else {
            return this.forceReply;
        }
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
