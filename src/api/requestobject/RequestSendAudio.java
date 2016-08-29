package api.requestobject;

import api.entity.*;

/**
 * Created by Gladiator on 2/6/2016 AD.
 */
public class RequestSendAudio {
    private Chat chat;
    private Audio audio;
    private InputFile inputFile;
    private int replyToMessageId;
    private boolean disableNotification;

    //Access these field by setReplyMarkup() and getReplyMarkup() functions
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ReplyKeyboardHide replyKeyboardHide;
    private ForceReply forceReply;

    public RequestSendAudio(){}

    public RequestSendAudio(Chat chat, Audio audio){
        this.chat = chat;
        this.audio = audio;
    }

    public RequestSendAudio(Object chatId, Audio audio){
        this.chat = new Chat(chatId);
        this.audio = audio;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public void setReplyMarkup(Object replyMarkup) {
        if (replyMarkup instanceof ReplyKeyboardMarkup) {
            this.replyKeyboardMarkup = (ReplyKeyboardMarkup) replyMarkup;
        } else if (replyMarkup instanceof ReplyKeyboardHide) {
            this.replyKeyboardHide = (ReplyKeyboardHide) replyMarkup;
        } else if (replyMarkup instanceof ForceReply) {
            this.forceReply = (ForceReply) replyMarkup;
        } else if (replyMarkup instanceof InlineKeyboardMarkup) {
            this.inlineKeyboardMarkup = (InlineKeyboardMarkup) replyMarkup;
        }
    }

    public Object getReplyMarkup(){
        if (this.replyKeyboardMarkup != null) {
            return this.replyKeyboardMarkup;
        }
        if (this.replyKeyboardHide != null) {
            return this.replyKeyboardHide;
        }
        if (this.forceReply != null) {
            return this.forceReply;
        }
        if (this.inlineKeyboardMarkup != null) {
            return this.inlineKeyboardMarkup;
        }
        return null;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public int getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    public InputFile getInputFile() {
        return inputFile;
    }

    public void setInputFile(InputFile inputFile) {
        this.inputFile = inputFile;
    }

    public boolean isDisableNotification() {
        return disableNotification;
    }

    public void setDisableNotification(boolean disableNotification) {
        this.disableNotification = disableNotification;
    }
}
