package api.requestObject;

import api.entity.*;

/**
 * Created by Gladiator on 2/4/2016 AD.
 */
public class RequestSendPhoto {
    private Chat chat;
    private PhotoSize photo;
    private InputFile newPhoto;
    private String caption;
    private Message replyToMessage;
    private ReplyKeyboardMarkup replyMarkup;
}
