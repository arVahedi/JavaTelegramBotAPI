package telegram.bot.api.entity;

import java.io.Serializable;

public class ReplyKeyboardHide implements Serializable {
    private boolean hide_keyboard = true;
    private boolean selective;

    public boolean isHideKeyboard() {
        return hide_keyboard;
    }

    public void setHideKeyboard(boolean hideKeyboard) {
        this.hide_keyboard = hideKeyboard;
    }

    public boolean isSelective() {
        return selective;
    }

    public void setSelective(boolean selective) {
        this.selective = selective;
    }
}
