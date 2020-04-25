package api.entity.inline;

import java.io.Serializable;
import java.util.List;

public class InlineKeyboardMarkup implements Serializable {
    private List<List<InlineKeyboardButton>> inline_keyboard;

    //region Getter and Setter
    public List<List<InlineKeyboardButton>> getInline_keyboard() {
        return inline_keyboard;
    }

    public void setInline_keyboard(List<List<InlineKeyboardButton>> inline_keyboard) {
        this.inline_keyboard = inline_keyboard;
    }
    //endregion
}
