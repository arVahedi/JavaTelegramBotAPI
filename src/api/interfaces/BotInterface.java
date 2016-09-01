package api.interfaces;

import api.entity.Message;
import api.entity.User;
import api.entity.UserProfilePhoto;
import api.requestobject.*;

import java.io.IOException;

/**
 *
 * Created by Gladiator on 8/26/2016 AD.
 */
public interface BotInterface {

    User getMe() throws IOException;

    Message sendMessage(RequestSendMessage requestSendMessage) throws IOException;

    Message forwardMessage(RequestForwardMessage requestForwardMessage) throws IOException;

    Message sendPhoto(RequestSendPhoto requestSendPhoto) throws IOException;

    Message sendAudio(RequestSendAudio requestSendAudio) throws IOException;

    Message sendDocument(RequestSendDocument requestSendDocument) throws IOException;

    Message sendSticker(RequestSendSticker requestSendSticker) throws IOException;

    Message sendVideo(RequestSendVideo requestSendVideo) throws IOException;

    Message sendVoice(RequestSendVoice requestSendVoice) throws IOException;

    Message sendLocation(RequestSendLocation requestSendLocation) throws IOException;

    Message sendVenue(RequestSendVenue requestSendVenue) throws IOException;

    Message sendContact(RequestSendContact requestSendContact) throws IOException;

    boolean sendChatAction(RequestSendChatAction requestSendChatAction) throws IOException;

    UserProfilePhoto getUserProfilePhotos(RequestGetUserProfilePhotos requestGetUserProfilePhotos) throws IOException;

}
