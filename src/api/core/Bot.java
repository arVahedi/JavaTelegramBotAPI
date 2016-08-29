package api.core;

import api.entity.File;
import api.entity.Message;
import api.entity.User;
import api.entity.UserProfilePhoto;
import api.exception.*;
import api.interfaces.BotInterface;
import api.json.JSONObject;
import api.net.MultipartFormData;
import api.net.SSLConnection;
import api.requestobject.*;
import api.utilities.JsonUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
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
     * @throws IOException
     */
    public User getMe() throws IOException {
        String getMeUrl = API_URL + token + "/getMe";
        SSLConnection sslConnection = new SSLConnection(getMeUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();
            return gson.fromJson(jsonResponse.get("result").toString(), User.class);
        } else {
            throw new GetMeException("Illegal response.");
        }
    }

    /**
     * Use this method to send text messages. On success, the sent Message is returned.
     *
     * @param requestSendMessage request send message
     * @return Message - The send message is returned.
     * @throws IOException
     */
    public Message sendMessage(RequestSendMessage requestSendMessage) throws IOException {
        String chatId;
        if (requestSendMessage.getChat().isValid()) {
            chatId = requestSendMessage.getChat().getChatId();
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
        if (requestSendMessage.getReplyMarkup() != null) {
            sendMessageUrl += "&reply_markup=" + JsonUtil.toJsonSerializable(requestSendMessage.getReplyMarkup());
        }

        SSLConnection sslConnection = new SSLConnection(sendMessageUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
            if ((boolean) jsonResponse.get("ok")) {
                return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
            } else {
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
     * @return send Message is returned.
     * @throws IOException
     */
    public Message forwardMessage(RequestForwardMessage requestForwardMessage) throws IOException {

        String chatId;
        if (requestForwardMessage.getChat().isValid()) {
            chatId = requestForwardMessage.getChat().getChatId();
        } else {
            throw new ForwardMessageException("Chat id or chat username is null");
        }

        String forwardUrl = API_URL + token + "/forwardMessage?chat_id=" + chatId
                + "&from_chat_id=" + requestForwardMessage.getMessage().getChat().getId()
                + "&message_id=" + requestForwardMessage.getMessage().getMessageId()
                + "&disable_notification=" + requestForwardMessage.isDisableNotification();
        SSLConnection sslConnection = new SSLConnection(forwardUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
            if ((boolean) jsonResponse.get("ok")) {
                return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
            } else {
                throw new ForwardMessageException("Illegal Response.");
            }
        } catch (Exception e) {
            throw new ForwardMessageException(e.getMessage());
        }

    }

    /**
     * Use this method to send photos. On success, the sent Message is returned.
     *
     * @param requestSendPhoto Request send photo
     * @return send Message is returned.
     * @throws IOException
     */
    public Message sendPhoto(RequestSendPhoto requestSendPhoto) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendPhoto?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendPhoto.getChat().isValid()) {
            chatId = requestSendPhoto.getChat().getChatId();
        } else {
            throw new SendPhotoException("Chat id or chat username is null");
        }

        if (requestSendPhoto.getReplyToMessage() != null) {
            attributes.put("reply_to_message_id", String.valueOf(requestSendPhoto.getReplyToMessage().getMessageId()));
        }

        if (requestSendPhoto.getCaption() != null) {
            attributes.put("caption", requestSendPhoto.getCaption());
        }

        if (requestSendPhoto.getReplyMarkup() != null) {
            attributes.put("reply_markup", JsonUtil.toJsonSerializable(requestSendPhoto.getReplyMarkup()));
        }

        attributes.put("disable_notification", String.valueOf(requestSendPhoto.isDisableNotification()));

        JSONObject jsonResponse;
        if (requestSendPhoto.getPhoto() != null) {      // We don't upload file. Using photo_id instead.
            attributes.put("photo", requestSendPhoto.getPhoto().getFileId());
            urlBuilder.append("chat_id=").append(chatId);
            attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
            SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
            try {
                jsonResponse = sslConnection.getSSLConnection();
            } catch (Exception e) {
                throw new SendPhotoException(e.getMessage());
            }
        } else if (requestSendPhoto.getInputFile() != null) {       // We are uploading photo file.
            attributes.put("chat_id", chatId);
            HashMap<String, java.io.File> fileMap = new HashMap<>(1);
            fileMap.put("photo", new java.io.File(requestSendPhoto.getInputFile().getPath()));
            MultipartFormData multipartFormData = new MultipartFormData(urlBuilder.toString(), attributes, fileMap);
            multipartFormData.initialize();
            jsonResponse = multipartFormData.send();
        } else {
            throw new SendPhotoException("Photo id or input file is null");
        }

        if ((boolean) jsonResponse.get("ok")) {
            return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
        } else {
            throw new SendPhotoException("Illegal Response.");
        }
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display them in the music player.
     * Your audio must be in the .mp3 format. On success, the sent Message is returned.
     * Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param requestSendAudio Request send audio
     * @return send Message is returned
     * @throws IOException
     */
    public Message sendAudio(RequestSendAudio requestSendAudio) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendAudio?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendAudio.getChat().isValid()) {
            chatId = requestSendAudio.getChat().getChatId();
        } else {
            throw new SendAudioException("Chat id or chat username is null");
        }

        attributes.put("disable_notification", String.valueOf(requestSendAudio.isDisableNotification()));

        if (requestSendAudio.getReplyMarkup() != null) {
            attributes.put("reply_markup", JsonUtil.toJsonSerializable(requestSendAudio.getReplyMarkup()));
        }

        if (requestSendAudio.getReplyToMessageId() != 0) {
            attributes.put("reply_to_message_id", String.valueOf(requestSendAudio.getReplyToMessageId()));
        }

        if (requestSendAudio.getAudio() != null) {
            if (requestSendAudio.getAudio().getDuration() != 0) {
                attributes.put("duration", String.valueOf(requestSendAudio.getAudio().getDuration()));
            }
            if (requestSendAudio.getAudio().getPerformer() != null) {
                attributes.put("performer", requestSendAudio.getAudio().getPerformer());
            }
            if (requestSendAudio.getAudio().getTitle() != null) {
                attributes.put("title", requestSendAudio.getAudio().getTitle());
            }
        }

        JSONObject jsonResponse;
        if (requestSendAudio.getAudio() != null && requestSendAudio.getAudio().getFileId() != null) {   // We don't upload audio; We used file_id instead.
            attributes.put("audio", requestSendAudio.getAudio().getFileId());
            urlBuilder.append("chat_id=").append(chatId);
            attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
            SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
            try {
                jsonResponse = sslConnection.getSSLConnection();
            } catch (Exception e) {
                throw new SendAudioException(e.getMessage());
            }
        } else if (requestSendAudio.getInputFile() != null) {   // We are uploading audio file;
            attributes.put("chat_id", chatId);
            HashMap<String, java.io.File> fileMap = new HashMap<>(1);
            fileMap.put("audio", new java.io.File(requestSendAudio.getInputFile().getPath()));
            MultipartFormData multipartFormData = new MultipartFormData(urlBuilder.toString(), attributes, fileMap);
            multipartFormData.initialize();
            jsonResponse = multipartFormData.send();
        } else {
            throw new SendAudioException("Audio id and input file is null");
        }

        if ((boolean) jsonResponse.get("ok")) {
            return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
        } else {
            throw new SendAudioException("Illegal Response.");
        }
    }

    /**
     *Use this method to send general files. On success, the sent Message is returned.
     * Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
     *
     * @param requestSendDocument Request send document
     *
     * @return send Message is returned
     *
     * @throws IOException
     */
    public Message sendDocument(RequestSendDocument requestSendDocument) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendDocument?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendDocument.getChat().isValid()) {
            chatId = requestSendDocument.getChat().getChatId();
        } else {
            throw new SendDocumentException("Chat id and chat username is null");
        }

        attributes.put("disable_notification", String.valueOf(requestSendDocument.isDisableNotification()));

        if (requestSendDocument.getReplyToMessageId() != 0) {
            attributes.put("reply_to_message_id", String.valueOf(requestSendDocument.getReplyToMessageId()));
        }

        if (requestSendDocument.getReplyMarkup() != null) {
            attributes.put("reply_markup", JsonUtil.toJsonSerializable(requestSendDocument.getReplyMarkup()));
        }

        if (requestSendDocument.getCaption() != null){
            attributes.put("caption", requestSendDocument.getCaption());
        }

        JSONObject jsonResponse;
        if (requestSendDocument.getDocument() != null){     // We don't upload file. Using file_id instead.
            attributes.put("document", requestSendDocument.getDocument().getFileId());
            urlBuilder.append("chat_id=").append(chatId);
            attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
            SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
            try {
                jsonResponse = sslConnection.getSSLConnection();
            } catch (Exception e) {
                throw new SendDocumentException(e.getMessage());
            }
        } else if (requestSendDocument.getInputFile() != null){
            attributes.put("chat_id", chatId);
            HashMap<String, java.io.File> fileMap = new HashMap<>(1);
            fileMap.put("document", new java.io.File(requestSendDocument.getInputFile().getPath()));
            MultipartFormData multipartFormData = new MultipartFormData(urlBuilder.toString(), attributes, fileMap);
            multipartFormData.initialize();
            jsonResponse = multipartFormData.send();
        } else {
            throw new SendDocumentException("Document id and input file is null.");
        }

        if ((boolean) jsonResponse.get("ok")) {
            return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
        } else {
            throw new SendDocumentException("Illegal Response.");
        }
    }

    /**
     * Use this method to send .webp stickers. On success, the sent Message is returned.
     *
     * @param requestSendSticker Request send sticker
     *
     * @return send Message is returned
     *
     * @throws IOException
     */
    public Message sendSticker(RequestSendSticker requestSendSticker) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendSticker?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendSticker.getChat().isValid()) {
            chatId = requestSendSticker.getChat().getChatId();
        } else {
            throw new SendStickerException("Chat id and chat username is null");
        }

        attributes.put("disable_notification", String.valueOf(requestSendSticker.isDisableNotification()));

        if (requestSendSticker.getReplyToMessageId() != 0) {
            attributes.put("reply_to_message_id", String.valueOf(requestSendSticker.getReplyToMessageId()));
        }

        if (requestSendSticker.getReplyMarkup() != null) {
            attributes.put("reply_markup", JsonUtil.toJsonSerializable(requestSendSticker.getReplyMarkup()));
        }

        JSONObject jsonResponse;
        if (requestSendSticker.getSticker() != null){       // We don't upload sticker. Using file_id instead.
            attributes.put("sticker", requestSendSticker.getSticker().getFileId());
            urlBuilder.append("chat_id=").append(chatId);
            attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
            SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
            try {
                jsonResponse = sslConnection.getSSLConnection();
            } catch (Exception e) {
                throw new SendStickerException(e.getMessage());
            }
        } else if (requestSendSticker.getInputFile() != null){      // We are uploading sticker.
            attributes.put("chat_id", chatId);
            HashMap<String, java.io.File> fileMap = new HashMap<>(1);
            fileMap.put("sticker", new java.io.File(requestSendSticker.getInputFile().getPath()));
            MultipartFormData multipartFormData = new MultipartFormData(urlBuilder.toString(), attributes, fileMap);
            multipartFormData.initialize();
            jsonResponse = multipartFormData.send();
        } else {
            throw new SendStickerException("Sticker id and input file is null.");
        }

        if ((boolean) jsonResponse.get("ok")) {
            return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
        } else {
            throw new SendStickerException("Illegal Response.");
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
