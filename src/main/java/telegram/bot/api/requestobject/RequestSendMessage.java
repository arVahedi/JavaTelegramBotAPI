package telegram.bot.api.requestobject;

import telegram.bot.api.entity.*;
import telegram.bot.api.enums.ParseModeEnum;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gladiator on 1/23/2016 AD.
 */
public class RequestSendMessage {
    //region Required Fields
    private Chat chat;
    private String text;
    //endregion

    //region Optional Fields
    private ParseModeEnum parseMode; //Markdown or HTML; user ParseModeEnum class.
    private boolean disableWebPagePreview = false;
    private boolean disableNotification = false;
    private int replyToMessageId;

    //Access these field by setReplyMarkup() and getReplyMarkup() functions
    private InlineKeyboardMarkup inlineKeyboardMarkup;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ReplyKeyboardHide replyKeyboardHide;
    private ForceReply forceReply;
    //endregion

    public List<String> getUrlEncodedSafeLengthText(int length) throws UnsupportedEncodingException {
        int maximumValidLength = 4096;
        List<String> urlEncodedMessageParts = new ArrayList<>();
//        String[] messagesParts = this.text.split("(?<=\\G.{" + length + "})");

        List<String> messagesParts = new ArrayList<>();
        int index = 0;
        while (index < this.text.length()) {
            messagesParts.add(this.text.substring(index, index + Math.min(length, this.text.length() - index)));
            index = index + Math.min(length, this.text.length() - index);
        }

        for (String part : messagesParts) {
            String urlEncodedPart = URLEncoder.encode(part, StandardCharsets.UTF_8.name());
            if (urlEncodedPart.length() > maximumValidLength) {
                return this.getUrlEncodedSafeLengthText((length * 70) / 100);
            } else {
                urlEncodedMessageParts.add(urlEncodedPart);
            }
        }

        return urlEncodedMessageParts;
    }

    //region Getter and Setter
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
        if (this.inlineKeyboardMarkup != null) {
            return this.inlineKeyboardMarkup;
        }
        if (this.forceReply != null) {
            return this.forceReply;
        }
        return null;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public boolean isDisableNotification() {
        return disableNotification;
    }

    public void setDisableNotification(boolean disableNotification) {
        this.disableNotification = disableNotification;
    }
    //endregion
}
