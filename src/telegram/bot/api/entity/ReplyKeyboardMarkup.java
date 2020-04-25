package telegram.bot.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMarkup implements Serializable {
    private List<List<KeyboardButton>> keyboard = new ArrayList<>();
    private boolean resize_keyboard;
    private boolean one_time_keyboard;
    private boolean selective;

    public List<List<KeyboardButton>> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(List<List<KeyboardButton>> keyboard) {
        this.keyboard = keyboard;
    }

    public boolean isResizeKeyboard() {
        return resize_keyboard;
    }

    public void setResizeKeyboard(boolean resizeKeyboard) {
        this.resize_keyboard = resizeKeyboard;
    }

    public boolean isOneTimeKeyboard() {
        return one_time_keyboard;
    }

    public void setOneTimeKeyboard(boolean oneTimeKeyboard) {
        this.one_time_keyboard = oneTimeKeyboard;
    }

    public boolean isSelective() {
        return selective;
    }

    public void setSelective(boolean selective) {
        this.selective = selective;
    }
}
