package api.requestobject;

import api.entity.*;

/**
 * Created by Gladiator on 2/25/2016 AD.
 */
public class RequestSendDocument {
    private Chat chat;
    private Document document;
    private InputFile inputFile;
    private String caption;
    private boolean disableNotification = false;
    private int replyToMessageId;

    //Access these field by setReplyMarkup() and getReplyMarkup() functions
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ReplyKeyboardHide replyKeyboardHide;
    private ForceReply forceReply;

    public RequestSendDocument() {
    }

    public RequestSendDocument(Chat chat, Document document) {
        this.chat = chat;
        this.document = document;
    }

    public RequestSendDocument(Object chatId, Document document) {
        this.chat = new Chat(chatId);
        this.document = document;
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

    public Object getReplyMarkup() {
        if (this.replyKeyboardMarkup != null) {
            return this.replyKeyboardMarkup;
        }
        if (this.replyKeyboardHide != null) {
            return this.replyKeyboardHide;
        } else {
            return this.forceReply;
        }
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public InputFile getInputFile() {
        return inputFile;
    }

    public void setInputFile(InputFile inputFile) {
        this.inputFile = inputFile;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public boolean isDisableNotification() {
        return disableNotification;
    }

    public void setDisableNotification(boolean disableNotification) {
        this.disableNotification = disableNotification;
    }

    public int getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }
}
