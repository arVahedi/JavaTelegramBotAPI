package telegram.bot.api.requestobject;

import telegram.bot.api.entity.*;

/**
 * Created by Gladiator on 8/31/2016 AD.
 */
public class RequestSendVenue {
    //region Fields
    private Chat chat;
    private Venue venue;
    private boolean disableNotification = false;
    private int replyToMessageId;

    //Access these field by setReplyMarkup() and getReplyMarkup() functions
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ReplyKeyboardHide replyKeyboardHide;
    private ForceReply forceReply;
    //endregion

    //region Constructors
    public RequestSendVenue() {
    }

    public RequestSendVenue(Chat chat, Location location) {
        this.chat = chat;
        this.venue = new Venue(location);
    }

    public RequestSendVenue(Object chatId, Location location) {
        this.chat = new Chat(chatId);
        this.venue = new Venue(location);
    }

    public RequestSendVenue(Chat chat, Venue venue) {
        this.chat = chat;
        this.venue = venue;
    }

    public RequestSendVenue(Object chatId, Venue venue) {
        this.chat = new Chat(chatId);
        this.venue = venue;
    }
    //endregion

    //region Getter and Setter
    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
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

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    //endregion
}
