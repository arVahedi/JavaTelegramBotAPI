package telegram.bot.api.requestobject;

import telegram.bot.api.entity.*;

/**
 * Created by Gladiator on 1/23/2016 AD.
 */
public class RequestSendLocation {
    private Chat chat;
    private Location location;
    private boolean disableNotification = false;
    private int replyToMessageId;

    //Access these field by setReplyMarkup() and getReplyMarkup() functions
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ReplyKeyboardHide replyKeyboardHide;
    private ForceReply forceReply;

    public RequestSendLocation() {
    }

    public RequestSendLocation(Chat chat, Location location) {
        this.chat = chat;
        this.location = location;
    }

    public RequestSendLocation(Object chatId, double latitude, double longitude) {
        this.chat = new Chat(chatId);
        this.location = new Location(longitude, latitude);
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

    public Object getReplyMarkup() {
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getReplyToMessageId() {
        return replyToMessageId;
    }

    public void setReplyToMessageId(int replyToMessageId) {
        this.replyToMessageId = replyToMessageId;
    }

    public boolean isDisableNotification() {
        return disableNotification;
    }

    public void setDisableNotification(boolean disableNotification) {
        this.disableNotification = disableNotification;
    }
}
