package telegram.bot.api.entity;

import java.io.Serializable;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public class InlineKeyboardButton implements Serializable {
    private String text;
    private String url;
    private String callback_data;
    private String switch_inline_query;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCallback_data() {
        return callback_data;
    }

    public void setCallback_data(String callback_data) {
        this.callback_data = callback_data;
    }

    public String getSwitch_inline_query() {
        return switch_inline_query;
    }

    public void setSwitch_inline_query(String switch_inline_query) {
        this.switch_inline_query = switch_inline_query;
    }
}
