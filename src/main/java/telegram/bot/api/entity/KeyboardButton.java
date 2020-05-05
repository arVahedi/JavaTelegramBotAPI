package telegram.bot.api.entity;

import java.io.Serializable;

/**
 * Created by gladiator on 8/29/16.
 */
public class KeyboardButton implements Serializable {
    private String text;
    private boolean request_contact;
    private boolean request_location;

    public KeyboardButton(){}

    public KeyboardButton(String text){
        this.text = text;
    }

    public KeyboardButton(String text, boolean request_contact, boolean request_location){
        this.text = text;
        this.request_contact = request_contact;
        this.request_location = request_location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRequest_contact() {
        return request_contact;
    }

    public void setRequest_contact(boolean request_contact) {
        this.request_contact = request_contact;
    }

    public boolean isRequest_location() {
        return request_location;
    }

    public void setRequest_location(boolean request_location) {
        this.request_location = request_location;
    }
}
