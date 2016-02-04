package api.requestObject;

import api.entity.Chat;
import api.entity.Location;
import api.entity.ReplyKeyboardMarkup;

/**
 * Created by Gladiator on 1/23/2016 AD.
 */
public class RequestSendLocation {
    private Chat chat;
    private Location location;
    private int replyToMessageId;
    private ReplyKeyboardMarkup replyMarkup;

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

    public ReplyKeyboardMarkup getReplyMarkup() {
        return replyMarkup;
    }

    public void setReplyMarkup(ReplyKeyboardMarkup replyMarkup) {
        this.replyMarkup = replyMarkup;
    }
}
