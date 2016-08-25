package api.requestObject;

import api.entity.*;

/**
 * Created by Gladiator on 2/6/2016 AD.
 */
public class RequestSendAudio {
    private Chat chat;
    private Audio audio;
    private InputFile inputFile;
    private int replyToMessageId;

    //Access these field by setReplyMarkup() and getReplyMarkup() functions
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

}
