package api.core;

import api.entity.File;
import api.entity.Message;
import api.entity.User;
import api.entity.UserProfilePhoto;
import api.exception.*;
import api.interfaces.BotInterface;
import api.json.JSONObject;
import api.net.SSLConnection;
import api.requestobject.*;
import api.utilities.JsonUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Gladiator on 8/26/2016 AD.
 */
public class Bot implements BotInterface {
    private String id;
    private String username;
    private static String token;

    private static Bot instance = null;

    private final static String API_URL = "https://api.telegram.org/bot";

    private Bot(String token) {
        Bot.token = token;
    }

    public static Bot getInstance() {
        if (instance == null) {
            instance = new Bot(token);
        }

        return instance;
    }

    /**
     * A simple method for testing your bot's auth token.
     * Requires no parameters. Returns basic information about the bot in form of a User object.
     *
     * @return {@link api.entity.User User} - Basic information about the bot
     *
     * @throws IOException
     */
    public User getMe() throws IOException {
        String getMeUrl = API_URL + token + "/getMe";
        SSLConnection sslConnection = new SSLConnection(getMeUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        User user = new User();
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();
            user = gson.fromJson(jsonResponse.get("result").toString(), User.class);
        } else {
           throw new GetMeException("Illegal response.");
        }

        return user;
    }

    /**
     * Use this method to send text messages. On success, the sent Message is returned.
     *
     * @param requestSendMessage request send message
     *
     * @return Message - The send message is returned.
     *
     * @throws IOException
     */
    public Message sendMessage(RequestSendMessage requestSendMessage) throws IOException {
        String chatId;
        if (requestSendMessage.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendMessage.getChat().getId());
        } else if (requestSendMessage.getChat().getUsername() != null) {
            chatId = requestSendMessage.getChat().getUsername();
        } else {
            throw new SendMessageException("Chat id or chat username is null");
        }

        String sendMessageUrl = API_URL + token + "/sendMessage?chat_id=" + chatId
                + "&text=" + URLEncoder.encode(requestSendMessage.getText(), "UTF-8");


        sendMessageUrl = sendMessageUrl + "&disable_web_page_preview=" + requestSendMessage.getDisableWebPagePreview();
        sendMessageUrl = sendMessageUrl + "&disable_notification=" + requestSendMessage.isDisableNotification();

        if (requestSendMessage.getParseMode() != null) {
            sendMessageUrl = sendMessageUrl + "&parse_mode=" + requestSendMessage.getParseMode();
        }
        if (requestSendMessage.getReplyToMessageId() != 0) {
            sendMessageUrl = sendMessageUrl + "&reply_to_message_id=" + requestSendMessage.getReplyToMessageId();
        }
        if (requestSendMessage.getReplyMarkup() != null){
            sendMessageUrl += "&reply_markup=" + JsonUtil.toJsonSerializable(requestSendMessage.getReplyMarkup());
        }

        SSLConnection sslConnection = new SSLConnection(sendMessageUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
            if ((boolean) jsonResponse.get("ok")) {
                Message message = (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
                return message;
            }else{
                throw new SendMessageException("Illegal response.");
            }
        } catch (Exception e) {
            throw new SendMessageException(e.getMessage());
        }
    }

    /**
     * Use this method to forward messages of any kind. On success, the sent Message is returned.
     *
     * @param requestForwardMessage Request forward message
     *
     * @return send Message is returned.
     *
     * @throws IOException
     */
    public Message forwardMessage(RequestForwardMessage requestForwardMessage) throws IOException {

        String chatId;
        if (requestForwardMessage.getChat().getId() != 0) {
            chatId = String.valueOf(requestForwardMessage.getChat().getId());
        } else if (requestForwardMessage.getChat().getUsername() != null) {
            chatId = requestForwardMessage.getChat().getUsername();
        } else {
            throw new ForwardMessageException("Chat id or chat username is null");
        }

        String forwardUrl = API_URL + this.token + "/forwardMessage?chat_id=" + chatId
                + "&from_chat_id=" + requestForwardMessage.getMessage().getChat().getId()
                + "&message_id=" + requestForwardMessage.getMessage().getMessageId()
                + "&disable_notification=" + requestForwardMessage.isDisableNotification();
        SSLConnection sslConnection = new SSLConnection(forwardUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
            if ((boolean) jsonResponse.get("ok")) {
                Message message = (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
                return message;
            }else{
                throw new ForwardMessageException("Illegal Response.");
            }
        } catch (Exception e) {
            throw new ForwardMessageException(e.getMessage());
        }

    }

    public Message sendPhoto(RequestSendPhoto requestSendPhoto) {
        String chatId;
        if (requestSendPhoto.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendPhoto.getChat().getId());
        } else if (requestSendPhoto.getChat().getUsername() != null) {
            chatId = requestSendPhoto.getChat().getUsername();
        } else {
            throw new SendPhotoException("Chat id or chat username is null");
        }

        String photoId;
        if (requestSendPhoto.getPhoto().getFileId() != null) {
            photoId = requestSendPhoto.getPhoto().getFileId();
        } else if (requestSendPhoto.getInputFile() != null) {
            // TODO: how to send input file via multipart-form-data
            photoId = "";
        } else {
            throw new SendPhotoException("Photo id or input file is null");
        }

        String sendPhotoUrl = API_URL + token + "/sendPhoto?chat_id=" + chatId
                + "&photo=" + photoId + "&disableNotification=" + requestSendPhoto.isDisableNotification();

        if (requestSendPhoto.getReplyToMessage() != null) {
            sendPhotoUrl = sendPhotoUrl + "&reply_to_message_id=" + requestSendPhoto.getReplyToMessage().getMessageId();
        }

        if (requestSendPhoto.getCaption() != null) {
            sendPhotoUrl = sendPhotoUrl + "&caption=" + requestSendPhoto.getCaption();
        }

        if (requestSendPhoto.getReplyMarkup() != null){
            sendPhotoUrl += "&reply_markup=" + JsonUtil.toJsonSerializable(requestSendPhoto.getReplyMarkup());
        }

        SSLConnection sslConnection = new SSLConnection(sendPhotoUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
            if ((boolean) jsonResponse.get("ok")) {
                Message message = (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
                return message;
            }else{
                throw new ForwardMessageException("Illegal Response.");
            }
        } catch (Exception e) {
            throw new SendChatActionException(e.getMessage());
        }
    }

    public List<Message> getUpdates(RequestGetUpdate requestGetUpdate) throws IOException {
        String updateUrl = API_URL + token + "/getUpdates?";

        if (requestGetUpdate.getOffset() != 0) {
            if (!(updateUrl.endsWith("?"))) {
                updateUrl = updateUrl + "&";
            }
            updateUrl = updateUrl + "offset=" + requestGetUpdate.getOffset();
        }
        if (requestGetUpdate.getLimit() != 0) {
            if (!(updateUrl.endsWith("?"))) {
                updateUrl = updateUrl + "&";
            }
            updateUrl = updateUrl + "limit=" + requestGetUpdate.getLimit();
        }
        if (requestGetUpdate.getTimeout() != 0) {
            if (!(updateUrl.endsWith("?"))) {
                updateUrl = updateUrl + "&";
            }
            updateUrl = updateUrl + "timeout=" + requestGetUpdate.getTimeout();
        }

        SSLConnection sslConnection = new SSLConnection(updateUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        List<Message> listOfAllMessage = new ArrayList<Message>();
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();

            for (int counter = 0; counter < jsonResponse.getJSONArray("result").length(); counter++) {
                Message message = gson.fromJson(jsonResponse.getJSONArray("result").getJSONObject(counter).get("message").toString()
                        , Message.class);
                message.setUpdateId((Integer) jsonResponse.getJSONArray("result").getJSONObject(counter).get("update_id"));
                listOfAllMessage.add(message);
            }
        } else {
            //TODO: write telegram error message in exception;
            throw new GetUpdateException();
        }

        return listOfAllMessage;
    }

    public void sendLocation(RequestSendLocation requestSendLocation) {
        String chatId;
        if (requestSendLocation.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendLocation.getChat().getId());
        } else if (requestSendLocation.getChat().getUsername() != null) {
            chatId = requestSendLocation.getChat().getUsername();
        } else {
            throw new SendLocationException("Chat id or chat username is null");
        }

        String sendLocationUrl = API_URL + token + "/sendLocation?chat_id=" + chatId
                + "&latitude=" + requestSendLocation.getLocation().getLatitude()
                + "&longitude=" + requestSendLocation.getLocation().getLongitude();

        if (requestSendLocation.getReplyToMessageId() != 0) {
            sendLocationUrl = sendLocationUrl + "&reply_to_message_id=" + requestSendLocation.getReplyToMessageId();
        }
        //TODO: add replyMarkup support;

        SSLConnection sslConnection = new SSLConnection(sendLocationUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendLocationException(e.getMessage());
        }
    }

    public void sendChatAction(RequestSendChatAction requestSendChatAction) {
        String chatId;
        if (requestSendChatAction.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendChatAction.getChat().getId());
        } else if (requestSendChatAction.getChat().getUsername() != null) {
            chatId = requestSendChatAction.getChat().getUsername();
        } else {
            throw new SendChatActionException("Chat id or chat username is null");
        }

        String sendChatActionUrl = API_URL + token + "/sendChatAction?chat_id=" + chatId
                + "&action=" + requestSendChatAction.getAction();

        SSLConnection sslConnection = new SSLConnection(sendChatActionUrl);
        try {
            JSONObject jsonObject = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendChatActionException(e.getMessage());
        }
    }

    public UserProfilePhoto getUserProfilePhotos(RequestGetUserProfilePhotos requestGetUserProfilePhotos) throws IOException {
        String getUserProfilePhotoUrl = API_URL + token + "/getUserProfilePhotos?user_id="
                + requestGetUserProfilePhotos.getUser().getId();

        if (requestGetUserProfilePhotos.getLimit() != 0) {
            getUserProfilePhotoUrl = getUserProfilePhotoUrl + "&limit=" + requestGetUserProfilePhotos.getLimit();
        }
        if (requestGetUserProfilePhotos.getOffset() != 0) {
            getUserProfilePhotoUrl = getUserProfilePhotoUrl + "&offset=" + requestGetUserProfilePhotos.getOffset();
        }

        SSLConnection sslConnection = new SSLConnection(getUserProfilePhotoUrl);

        JSONObject jsonResponse = sslConnection.getSSLConnection();

        UserProfilePhoto userProfilePhoto;
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();
            userProfilePhoto = gson.fromJson(jsonResponse.get("result").toString(), UserProfilePhoto.class);
        } else {
            //TODO: write telegram error message exception;
            throw new GetUserProfilePhotosException();
        }

        return userProfilePhoto;
    }

    public File getFile(RequestGetFile requestGetFile) throws IOException {
        String getFileUrl = API_URL + token + "/getFile?file_id=" + requestGetFile.getFile().getFile_id();

        SSLConnection sslConnection = new SSLConnection(getFileUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        File file;
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();
            file = gson.fromJson(jsonResponse.get("result").toString(), File.class);
        } else {
            //TODO: write telegram error message exception;
            throw new GetFileException();
        }

        return file;
    }

    public void sendAudio(RequestSendAudio requestSendAudio) {
        String chatId;
        if (requestSendAudio.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendAudio.getChat().getId());
        } else if (requestSendAudio.getChat().getUsername() != null) {
            chatId = requestSendAudio.getChat().getUsername();
        } else {
            throw new SendAudioException("Chat id or chat username is null");
        }

        String audioId;
        if (requestSendAudio.getAudio().getFileId() != null) {
            audioId = requestSendAudio.getAudio().getFileId();
        } else if (requestSendAudio.getInputFile() != null) {
            // TODO: how to send input file via multipart-form-data
            audioId = "";
        } else {
            throw new SendAudioException("Audio id or input file is null");
        }

        String sendAudioUrl = API_URL + token + "/sendAudio?chat_id=" + chatId
                + "&audio=" + audioId;

        if (requestSendAudio.getAudio().getDuration() != 0) {
            sendAudioUrl = sendAudioUrl + "&duration=" + requestSendAudio.getAudio().getDuration();
        }

        if (requestSendAudio.getAudio().getPerformer() != null) {
            sendAudioUrl = sendAudioUrl + "&performer=" + requestSendAudio.getAudio().getPerformer();
        }

        if (requestSendAudio.getAudio().getTitle() != null) {
            sendAudioUrl = sendAudioUrl + "&title=" + requestSendAudio.getAudio().getTitle();
        }

        if (requestSendAudio.getReplyToMessageId() != 0) {
            sendAudioUrl = sendAudioUrl + "&reply_to_message_id=" + requestSendAudio.getReplyToMessageId();
        }

        //TODO: add replyMarkup support;

        SSLConnection sslConnection = new SSLConnection(sendAudioUrl);
        try {
            JSONObject jsonObject = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendChatActionException(e.getMessage());
        }

    }

    public void sendDocument(RequestSendDocument requestSendDocument) {
        String chatId;
        if (requestSendDocument.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendDocument.getChat().getId());
        } else if (requestSendDocument.getChat().getUsername() != null) {
            chatId = requestSendDocument.getChat().getUsername();
        } else {
            throw new SendDocumentException("Chat id or chat username is null");
        }

        String documentId;
        if (requestSendDocument.getDocument().getFileId() != null) {
            documentId = requestSendDocument.getDocument().getFileId();
        } else if (requestSendDocument.getInputFile() != null) {
            // TODO: how to send input file via multipart-form-data
            documentId = "";
        } else {
            throw new SendDocumentException("Document id or input file is null");
        }

        String sendDocumentUrl = API_URL + token + "/sendDocument?chat_id=" + chatId
                + "&document=" + documentId + "&disableNotification=" + requestSendDocument.isDisableNotification();

        if (requestSendDocument.getReplyToMessageId() != 0) {
            sendDocumentUrl = sendDocumentUrl + "&reply_to_message_id=" + requestSendDocument.getReplyToMessageId();
        }

        if (requestSendDocument.getCaption() != null) {
            sendDocumentUrl = sendDocumentUrl + "&caption=" + requestSendDocument.getCaption();
        }

        //TODO: add replyMarkup support;

        SSLConnection sslConnection = new SSLConnection(sendDocumentUrl);
        try {
            JSONObject jsonObject = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendChatActionException(e.getMessage());
        }

    }

    public void sendSticker(RequestSendSticker requestSendSticker) {
        String chatId;
        if (requestSendSticker.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendSticker.getChat().getId());
        } else if (requestSendSticker.getChat().getUsername() != null) {
            chatId = requestSendSticker.getChat().getUsername();
        } else {
            throw new SendStickerException("Chat id or chat username is null");
        }

        String stickerId;
        if (requestSendSticker.getSticker().getFileId() != null) {
            stickerId = requestSendSticker.getSticker().getFileId();
        } else if (requestSendSticker.getInputFile() != null) {
            // TODO: how to send input file via multipart-form-data
            stickerId = "";
        } else {
            throw new SendStickerException("Sticker id or input file is null");
        }

        String sendStickerUrl = API_URL + token + "/sendSticker?chat_id=" + chatId
                + "&sticker=" + stickerId + "&disableNotification=" + requestSendSticker.isDisableNotification();

        if (requestSendSticker.getReplyToMessageId() != 0) {
            sendStickerUrl = sendStickerUrl + "&reply_to_message_id=" + requestSendSticker.getReplyToMessageId();
        }

        //TODO: add replyMarkup support;

        SSLConnection sslConnection = new SSLConnection(sendStickerUrl);
        try {
            JSONObject jsonObject = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendChatActionException(e.getMessage());
        }

    }

    public void sendVideo(RequestSendVideo requestSendVideo) {
        String chatId;
        if (requestSendVideo.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendVideo.getChat().getId());
        } else if (requestSendVideo.getChat().getUsername() != null) {
            chatId = requestSendVideo.getChat().getUsername();
        } else {
            throw new SendVoiceException("Chat id or chat username is null");
        }

        String videoId;
        if (requestSendVideo.getVideo().getFileId() != null) {
            videoId = requestSendVideo.getVideo().getFileId();
        } else if (requestSendVideo.getInputFile() != null) {
            // TODO: how to send input file via multipart-form-data
            videoId = "";
        } else {
            throw new SendVoiceException("Video id or input file is null");
        }

        String sendVideoUrl = API_URL + token + "/sendVideo?chat_id=" + chatId
                + "&video=" + videoId + "&disableNotification=" + requestSendVideo.isDisableNotification();

        if (requestSendVideo.getReplyToMessageId() != 0) {
            sendVideoUrl = sendVideoUrl + "&reply_to_message_id=" + requestSendVideo.getReplyToMessageId();
        }

        if (requestSendVideo.getCaption() != null) {
            sendVideoUrl = sendVideoUrl + "&caption=" + requestSendVideo.getCaption();
        }

        if (requestSendVideo.getVideo().getDuration() != 0) {
            sendVideoUrl = sendVideoUrl + "&duration=" + requestSendVideo.getVideo().getDuration();
        }

        if (requestSendVideo.getVideo().getWidth() != 0) {
            sendVideoUrl = sendVideoUrl + "&width=" + requestSendVideo.getVideo().getWidth();
        }

        if (requestSendVideo.getVideo().getHeight() != 0) {
            sendVideoUrl = sendVideoUrl + "&height=" + requestSendVideo.getVideo().getHeight();
        }

        //TODO: add replyMarkup support;

        SSLConnection sslConnection = new SSLConnection(sendVideoUrl);
        try {
            JSONObject jsonObject = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendChatActionException(e.getMessage());
        }
    }

    public void sendVoice(RequestSendVoice requestSendVoice) {
        String chatId;
        if (requestSendVoice.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendVoice.getChat().getId());
        } else if (requestSendVoice.getChat().getUsername() != null) {
            chatId = requestSendVoice.getChat().getUsername();
        } else {
            throw new SendVoiceException("Chat id or chat username is null");
        }

        String voiceId;
        if (requestSendVoice.getVoice().getFileId() != null) {
            voiceId = requestSendVoice.getVoice().getFileId();
        } else if (requestSendVoice.getInputFile() != null) {
            // TODO: how to send input file via multipart-form-data
            voiceId = "";
        } else {
            throw new SendVoiceException("Voice id or input file is null");
        }

        String sendVoiceUrl = API_URL + token + "/sendVoice?chat_id=" + chatId
                + "&voice=" + voiceId + "&disableNotification=" + requestSendVoice.isDisableNotification();

        if (requestSendVoice.getReplyToMessageId() != 0) {
            sendVoiceUrl = sendVoiceUrl + "&reply_to_message_id=" + requestSendVoice.getReplyToMessageId();
        }

        if (requestSendVoice.getVoice().getDuration() != 0) {
            sendVoiceUrl = sendVoiceUrl + "&duration=" + requestSendVoice.getVoice().getDuration();
        }

        //TODO: add replyMarkup support;

        SSLConnection sslConnection = new SSLConnection(sendVoiceUrl);
        try {
            JSONObject jsonObject = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendChatActionException(e.getMessage());
        }
    }

    public void setWebHook(RequestSetWebHook requestSetWebHook) {
        //TODO: add input file for certificate file;
        String setWebHookUrl = API_URL + token + "/setWebhook?url=" + requestSetWebHook.getUrl() +
                "&certificate=" + "";

        SSLConnection sslConnection = new SSLConnection(setWebHookUrl);
        try {
            JSONObject jsonObject = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendChatActionException(e.getMessage());
        }
    }

    //TODO: other method for bot telegram entity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String getToken() {
        return Bot.token;
    }

    public static void setToken(String token) {
        Bot.token = token;
    }
}
