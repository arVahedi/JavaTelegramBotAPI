package api.entity;

import java.io.Serializable;

public class Update implements Serializable {
    //region Fields
    private int update_id;
    private Message message;
    private Message edited_message;
//    private InlineQuery inline_query;
//    private ChosenInlineResult chosen_inline_result;
    private CallbackQuery callback_query;
    //endregion

    //region Getter and Setter
    public int getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(int update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getEdited_message() {
        return edited_message;
    }

    public void setEdited_message(Message edited_message) {
        this.edited_message = edited_message;
    }

    public CallbackQuery getCallback_query() {
        return callback_query;
    }

    public void setCallback_query(CallbackQuery callback_query) {
        this.callback_query = callback_query;
    }
    //endregion
}
