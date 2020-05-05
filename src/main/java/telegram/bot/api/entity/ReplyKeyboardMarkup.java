package telegram.bot.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMarkup implements Serializable {
    private List<List<KeyboardButton>> keyboard = new ArrayList<>();
    @JsonProperty("resize_keyboard")
    private boolean resizeKeyboard;
    @JsonProperty("one_time_keyboard")
    private boolean oneTimeKeyboard;
    private boolean selective;

    public List<List<KeyboardButton>> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(List<List<KeyboardButton>> keyboard) {
        this.keyboard = keyboard;
    }

    public boolean isResizeKeyboard() {
        return resizeKeyboard;
    }

    public void setResizeKeyboard(boolean resizeKeyboard) {
        this.resizeKeyboard = resizeKeyboard;
    }

    public boolean isOneTimeKeyboard() {
        return oneTimeKeyboard;
    }

    public void setOneTimeKeyboard(boolean oneTimeKeyboard) {
        this.oneTimeKeyboard = oneTimeKeyboard;
    }

    public boolean isSelective() {
        return selective;
    }

    public void setSelective(boolean selective) {
        this.selective = selective;
    }
}
