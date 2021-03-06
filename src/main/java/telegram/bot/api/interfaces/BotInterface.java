package telegram.bot.api.interfaces;

import telegram.bot.api.entity.*;
import telegram.bot.api.requestobject.*;
import telegram.bot.api.requestobject.updatingrequest.RequestEditMessageCaption;
import telegram.bot.api.requestobject.updatingrequest.RequestEditMessageReplyMarkup;
import telegram.bot.api.requestobject.updatingrequest.RequestEditMessageText;

import java.io.IOException;
import java.util.List;

/**
 * Created by Gladiator on 8/26/2016 AD.
 */
public interface BotInterface {

    User getMe() throws IOException;

    List<Message> getUpdates(RequestGetUpdate requestGetUpdate) throws IOException;

    boolean setWebHook(RequestSetWebHook requestSetWebHook) throws IOException;

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

    File getFile(RequestGetFile requestGetFile) throws IOException;

    void downloadFile(RequestDownloadFile requestDownloadFile) throws IOException;

    boolean kickChatMember(RequestKickChatMember requestKickChatMember) throws IOException;

    boolean leaveChat(RequestLeaveChat requestLeaveChat) throws IOException;

    boolean unbanChatMember(RequestUnbanChatMember requestUnbanChatMember) throws IOException;

    Chat getChat(RequestGetChat requestGetChat) throws IOException;

    List<ChatMember> getChatAdministrators(RequestGetChatAdministrators requestGetChatAdministrators) throws IOException;

    int getChatMembersCount(RequestGetChatMembersCount requestGetChatMembersCount) throws IOException;

    ChatMember getChatMember(RequestGetChatMember requestGetChatMember) throws IOException;

    boolean answerCallbackQuery(RequestAnswerCallbackQuery requestAnswerCallbackQuery) throws IOException;

    Object editMessageText(RequestEditMessageText requestEditMessageText) throws IOException;

    Object editMessageCaption(RequestEditMessageCaption requestEditMessageCaption) throws IOException;

    Object editMessageReplyMarkup(RequestEditMessageReplyMarkup requestEditMessageReplyMarkup) throws IOException;
}
