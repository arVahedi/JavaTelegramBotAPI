package api.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public class InlineKeyboardMarkup implements Serializable {
    private ArrayList<ArrayList<InlineKeyboardButton>> inline_keyboard = new ArrayList<ArrayList<InlineKeyboardButton>>();

    public ArrayList<ArrayList<InlineKeyboardButton>> getInline_keyboard() {
        return inline_keyboard;
    }

    public void setInline_keyboard(ArrayList<ArrayList<InlineKeyboardButton>> inline_keyboard) {
        this.inline_keyboard = inline_keyboard;
    }
}
