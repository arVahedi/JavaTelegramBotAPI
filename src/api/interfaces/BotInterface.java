package api.interfaces;

import api.entity.Message;
import api.entity.User;
import api.requestobject.RequestForwardMessage;
import api.requestobject.RequestSendMessage;

import java.io.IOException;

/**
 *
 * Created by Gladiator on 8/26/2016 AD.
 */
public interface BotInterface {

    User getMe() throws IOException;

    Message sendMessage(RequestSendMessage requestSendMessage) throws IOException;

    Message forwardMessage(RequestForwardMessage requestForwardMessage) throws IOException;

}
