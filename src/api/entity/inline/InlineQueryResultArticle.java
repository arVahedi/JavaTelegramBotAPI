package api.entity.inline;

import api.entity.InputMessageContent;

public class InlineQueryResultArticle extends InlineQueryResult {
    private final String type = "article";
    private String id;
    private String title;
    private InputMessageContent input_message_content;
    private InlineKeyboardMarkup reply_markup;
    private String url;
    private boolean hide_url;
    private String description;
    private String thumb_url;
    private int thumb_width;
    private int thumb_height;

    //region Getter and Setter
    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InputMessageContent getInput_message_content() {
        return input_message_content;
    }

    public void setInput_message_content(InputMessageContent input_message_content) {
        this.input_message_content = input_message_content;
    }

    public InlineKeyboardMarkup getReply_markup() {
        return reply_markup;
    }

    public void setReply_markup(InlineKeyboardMarkup reply_markup) {
        this.reply_markup = reply_markup;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHide_url() {
        return hide_url;
    }

    public void setHide_url(boolean hide_url) {
        this.hide_url = hide_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public int getThumb_width() {
        return thumb_width;
    }

    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    public int getThumb_height() {
        return thumb_height;
    }

    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }
    //endregion
}
