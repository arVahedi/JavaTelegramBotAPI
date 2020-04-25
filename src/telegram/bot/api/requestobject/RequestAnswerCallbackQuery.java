package telegram.bot.api.requestobject;

import telegram.bot.api.entity.CallbackQuery;

import java.io.Serializable;

/**
 * Created by Gladiator on 9/5/2016 AD.
 */
public class RequestAnswerCallbackQuery implements Serializable {
    //region Fields
    private CallbackQuery callbackQuery;
    private String text;
    private boolean showAlert = false;
    //endregion

    //region Constructors
    public RequestAnswerCallbackQuery() {
    }

    public RequestAnswerCallbackQuery(CallbackQuery callbackQuery) {
        this.callbackQuery = callbackQuery;
    }

    public RequestAnswerCallbackQuery(String text) {
        this.text = text;
    }

    public RequestAnswerCallbackQuery(CallbackQuery callbackQuery, String text) {
        this(callbackQuery);
        this.text = text;
    }

    public RequestAnswerCallbackQuery(CallbackQuery callbackQuery, String text, boolean showAlert) {
        this(callbackQuery, text);
        this.showAlert = showAlert;
    }
    //endregion

    //region Getter and Setter
    public CallbackQuery getCallbackQuery() {
        return callbackQuery;
    }

    public void setCallbackQuery(CallbackQuery callbackQuery) {
        this.callbackQuery = callbackQuery;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isShowAlert() {
        return showAlert;
    }

    public void setShowAlert(boolean showAlert) {
        this.showAlert = showAlert;
    }
    //endregion
}
