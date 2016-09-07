package api.core;

import api.entity.*;
import api.enums.ChatMemberStatusEnum;
import api.exception.*;
import api.interfaces.BotInterface;
import api.json.JSONObject;
import api.net.MultipartFormData;
import api.net.SSLConnection;
import api.requestobject.*;
import api.utilities.JsonUtil;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gladiator on 8/26/2016 AD.
 * <p>
 * Updated for (May 25, 2016)
 */
public class Bot implements BotInterface {
    //region Fields
    private String id;
    private String username;
    private static String token;

    private static Bot instance = null;

    private final static String API_URL = "https://api.telegram.org/bot";
    private final static String API_DOWNLOAD_FILE_URL = "https://api.telegram.org/file/bot";
    //endregion

    //region Constructors
    private Bot(String token) {
        Bot.token = token;
    }
    //endregion

    public static Bot getInstance() {
        if (instance == null) {
            instance = new Bot(token);
        }

        return instance;
    }

    //region Bot action

    public static void initialize(String token) {
        Bot.token = token;
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
     *
     * @return Message - The send message is returned.
     *
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
            sendMessageUrl = sendMessageUrl + "&parse_mode=" + requestSendMessage.getParseMode().value();
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
     *
     * @return send Message is returned.
     *
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
     *
     * @return send Message is returned.
     *
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
     *
     * @return send Message is returned
     *
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
     * Use this method to send general files. On success, the sent Message is returned.
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

        if (requestSendDocument.getCaption() != null) {
            attributes.put("caption", requestSendDocument.getCaption());
        }

        JSONObject jsonResponse;
        if (requestSendDocument.getDocument() != null) {     // We don't upload file. Using file_id instead.
            attributes.put("document", requestSendDocument.getDocument().getFileId());
            urlBuilder.append("chat_id=").append(chatId);
            attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
            SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
            try {
                jsonResponse = sslConnection.getSSLConnection();
            } catch (Exception e) {
                throw new SendDocumentException(e.getMessage());
            }
        } else if (requestSendDocument.getInputFile() != null) {
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
        if (requestSendSticker.getSticker() != null) {       // We don't upload sticker. Using file_id instead.
            attributes.put("sticker", requestSendSticker.getSticker().getFileId());
            urlBuilder.append("chat_id=").append(chatId);
            attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
            SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
            try {
                jsonResponse = sslConnection.getSSLConnection();
            } catch (Exception e) {
                throw new SendStickerException(e.getMessage());
            }
        } else if (requestSendSticker.getInputFile() != null) {      // We are uploading sticker.
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

    /**
     * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document).
     * On success, the sent Message is returned. Bots can currently send video files of up to 50 MB in size,
     * this limit may be changed in the future.
     *
     * @param requestSendVideo Request send video
     *
     * @return send Message is returned
     *
     * @throws IOException
     */
    public Message sendVideo(RequestSendVideo requestSendVideo) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendVideo?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendVideo.getChat().isValid()) {
            chatId = requestSendVideo.getChat().getChatId();
        } else {
            throw new SendVideoException("Chat id and chat username is null");
        }

        attributes.put("disable_notification", String.valueOf(requestSendVideo.isDisableNotification()));

        if (requestSendVideo.getReplyToMessageId() != 0) {
            attributes.put("reply_to_message_id", String.valueOf(requestSendVideo.getReplyToMessageId()));
        }

        if (requestSendVideo.getReplyMarkup() != null) {
            attributes.put("reply_markup", JsonUtil.toJsonSerializable(requestSendVideo.getReplyMarkup()));
        }

        if (requestSendVideo.getCaption() != null) {
            attributes.put("caption", requestSendVideo.getCaption());
        }

        if (requestSendVideo.getVideo() != null) {
            if (requestSendVideo.getVideo().getDuration() != 0) {
                attributes.put("duration", String.valueOf(requestSendVideo.getVideo().getDuration()));
            }

            if (requestSendVideo.getVideo().getWidth() != 0) {
                attributes.put("width", String.valueOf(requestSendVideo.getVideo().getWidth()));
            }

            if (requestSendVideo.getVideo().getHeight() != 0) {
                attributes.put("height", String.valueOf(requestSendVideo.getVideo().getHeight()));
            }
        }

        JSONObject jsonResponse;
        if (requestSendVideo.getVideo() != null && requestSendVideo.getVideo().getFileId() != null) {   // We don't upload video; We used file_id instead.
            attributes.put("video", requestSendVideo.getVideo().getFileId());
            urlBuilder.append("chat_id=").append(chatId);
            attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
            SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
            try {
                jsonResponse = sslConnection.getSSLConnection();
            } catch (Exception e) {
                throw new SendVideoException(e.getMessage());
            }
        } else if (requestSendVideo.getInputFile() != null) {   // We are uploading video file;
            attributes.put("chat_id", chatId);
            HashMap<String, java.io.File> fileMap = new HashMap<>(1);
            fileMap.put("video", new java.io.File(requestSendVideo.getInputFile().getPath()));
            MultipartFormData multipartFormData = new MultipartFormData(urlBuilder.toString(), attributes, fileMap);
            multipartFormData.initialize();
            jsonResponse = multipartFormData.send();
        } else {
            throw new SendVideoException("Video id and input file is null");
        }

        if ((boolean) jsonResponse.get("ok")) {
            return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
        } else {
            throw new SendVideoException("Illegal Response.");
        }
    }

    /**
     * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message.
     * For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Audio or Document).
     * On success, the sent Message is returned. Bots can currently send voice messages of up to 50 MB in size,
     * this limit may be changed in the future.
     *
     * @param requestSendVoice Request send voice
     *
     * @return send Message is returned
     *
     * @throws IOException
     */
    public Message sendVoice(RequestSendVoice requestSendVoice) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendVoice?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendVoice.getChat().isValid()) {
            chatId = requestSendVoice.getChat().getChatId();
        } else {
            throw new SendVoiceException("Chat id and Chat username is null");
        }

        attributes.put("disable_notification", String.valueOf(requestSendVoice.isDisableNotification()));

        if (requestSendVoice.getReplyToMessageId() != 0) {
            attributes.put("reply_to_message_id", String.valueOf(requestSendVoice.getReplyToMessageId()));
        }

        if (requestSendVoice.getReplyMarkup() != null) {
            attributes.put("reply_markup", JsonUtil.toJsonSerializable(requestSendVoice.getReplyMarkup()));
        }

        if (requestSendVoice.getVoice() != null) {
            if (requestSendVoice.getVoice().getDuration() != 0) {
                attributes.put("duration", String.valueOf(requestSendVoice.getVoice().getDuration()));
            }
        }

        JSONObject jsonResponse;
        if (requestSendVoice.getVoice() != null && requestSendVoice.getVoice().getFileId() != null) {   // We don't upload voice; We used file_id instead.
            attributes.put("voice", requestSendVoice.getVoice().getFileId());
            urlBuilder.append("chat_id=").append(chatId);
            attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
            SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
            try {
                jsonResponse = sslConnection.getSSLConnection();
            } catch (Exception e) {
                throw new SendVoiceException(e.getMessage());
            }
        } else if (requestSendVoice.getInputFile() != null) {   // We are uploading voice file;
            attributes.put("chat_id", chatId);
            HashMap<String, java.io.File> fileMap = new HashMap<>(1);
            fileMap.put("voice", new java.io.File(requestSendVoice.getInputFile().getPath()));
            MultipartFormData multipartFormData = new MultipartFormData(urlBuilder.toString(), attributes, fileMap);
            multipartFormData.initialize();
            jsonResponse = multipartFormData.send();
        } else {
            throw new SendVideoException("Voice id and input file is null");
        }

        if ((boolean) jsonResponse.get("ok")) {
            return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
        } else {
            throw new SendVoiceException("Illegal Response.");
        }
    }

    /**
     * Use this method to send point on the map. On success, the sent Message is returned.
     *
     * @param requestSendLocation Request send location
     *
     * @return send Message is returned
     */
    public Message sendLocation(RequestSendLocation requestSendLocation) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendLocation?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendLocation.getChat().isValid()) {
            chatId = requestSendLocation.getChat().getChatId();
        } else {
            throw new SendLocationException("Chat id and chat username is null");
        }

        attributes.put("disable_notification", String.valueOf(requestSendLocation.isDisableNotification()));

        if (requestSendLocation.getReplyToMessageId() != 0) {
            attributes.put("reply_to_message_id", String.valueOf(requestSendLocation.getReplyToMessageId()));
        }

        if (requestSendLocation.getReplyMarkup() != null) {
            attributes.put("reply_markup", JsonUtil.toJsonSerializable(requestSendLocation.getReplyMarkup()));
        }

        attributes.put("latitude", String.valueOf(requestSendLocation.getLocation().getLatitude()));
        attributes.put("longitude", String.valueOf(requestSendLocation.getLocation().getLongitude()));

        urlBuilder.append("chat_id=").append(chatId);
        attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
        } else {
            throw new SendLocationException("Illegal Response.");
        }
    }

    /**
     * Use this method to send information about a venue. On success, the sent Message is returned.
     *
     * @param requestSendVenue Request send venue
     *
     * @return send Message is returned
     */
    public Message sendVenue(RequestSendVenue requestSendVenue) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendVenue?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendVenue.getChat().isValid()) {
            chatId = requestSendVenue.getChat().getChatId();
        } else {
            throw new SendVenueException("Chat id and chat username is null");
        }

        attributes.put("disable_notification", String.valueOf(requestSendVenue.isDisableNotification()));

        if (requestSendVenue.getReplyToMessageId() != 0) {
            attributes.put("reply_to_message_id", String.valueOf(requestSendVenue.getReplyToMessageId()));
        }

        if (requestSendVenue.getReplyMarkup() != null) {
            attributes.put("reply_markup", JsonUtil.toJsonSerializable(requestSendVenue.getReplyMarkup()));
        }

        attributes.put("latitude", String.valueOf(requestSendVenue.getVenue().getLocation().getLatitude()));
        attributes.put("longitude", String.valueOf(requestSendVenue.getVenue().getLocation().getLongitude()));
        attributes.put("title", requestSendVenue.getVenue().getTitle());
        attributes.put("address", requestSendVenue.getVenue().getAddress());

        if (requestSendVenue.getVenue().getFoursquare_id() != null) {
            attributes.put("foursquare_id", requestSendVenue.getVenue().getFoursquare_id());
        }

        urlBuilder.append("chat_id=").append(chatId);
        attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
        } else {
            throw new SendVenueException("Illegal Response.");
        }
    }

    /**
     * Use this method to send phone contacts. On success, the sent Message is returned.
     *
     * @param requestSendContact Request send contact
     *
     * @return send Message is returned
     */
    public Message sendContact(RequestSendContact requestSendContact) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendContact?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendContact.getChat().isValid()) {
            chatId = requestSendContact.getChat().getChatId();
        } else {
            throw new SendContactException("Chat id and chat username is null");
        }

        attributes.put("disable_notification", String.valueOf(requestSendContact.isDisableNotification()));

        if (requestSendContact.getReplyToMessageId() != 0) {
            attributes.put("reply_to_message_id", String.valueOf(requestSendContact.getReplyToMessageId()));
        }

        if (requestSendContact.getReplyMarkup() != null) {
            attributes.put("reply_markup", JsonUtil.toJsonSerializable(requestSendContact.getReplyMarkup()));
        }

        if (requestSendContact.getContact() != null) {
            attributes.put("phone_number", requestSendContact.getContact().getPhoneNumber());
            attributes.put("first_name", requestSendContact.getContact().getFirstName());
            if (requestSendContact.getContact().getLastName() != null) {
                attributes.put("last_name", requestSendContact.getContact().getLastName());
            }
        } else {
            throw new SendContactException("Contact object is null");
        }

        urlBuilder.append("chat_id=").append(chatId);
        attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return (Message) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Message.class);
        } else {
            throw new SendContactException("Illegal Response.");
        }
    }

    /**
     * Use this method when you need to tell the user that something is happening on the bot's side.
     * The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status).
     * We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
     *
     * @param requestSendChatAction Request send chat action
     *
     * @return boolean
     */
    public boolean sendChatAction(RequestSendChatAction requestSendChatAction) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/sendChatAction?");
        HashMap<String, String> attributes = new HashMap<>();

        String chatId;
        if (requestSendChatAction.getChat().isValid()) {
            chatId = requestSendChatAction.getChat().getChatId();
        } else {
            throw new SendChatActionException("Chat id or chat username is null");
        }

        attributes.put("action", requestSendChatAction.getAction().value());

        urlBuilder.append("chat_id=").append(chatId);
        attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return (boolean) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Boolean.class);
        } else {
            throw new SendChatActionException("Illegal Response.");
        }
    }

    /**
     * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
     *
     * @param requestGetUserProfilePhotos Request get user profile photos
     *
     * @return Returns a UserProfilePhotos object.
     *
     * @throws IOException
     */
    public UserProfilePhoto getUserProfilePhotos(RequestGetUserProfilePhotos requestGetUserProfilePhotos) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/getUserProfilePhotos?");
        HashMap<String, String> attributes = new HashMap<>();

        urlBuilder.append("user_id=").append(requestGetUserProfilePhotos.getUser().getId());

        if (requestGetUserProfilePhotos.getLimit() != 0) {
            attributes.put("limit", String.valueOf(requestGetUserProfilePhotos.getLimit()));
        }

        if (requestGetUserProfilePhotos.getOffset() != 0) {
            attributes.put("offset", String.valueOf(requestGetUserProfilePhotos.getOffset()));
        }

        attributes.forEach((key, value) -> urlBuilder.append("&").append(key).append("=").append(value));
        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        UserProfilePhoto userProfilePhoto;
        if ((boolean) jsonResponse.get("ok")) {
            userProfilePhoto = (UserProfilePhoto) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), UserProfilePhoto.class);
        } else {
            throw new GetUserProfilePhotosException("Illegal Response.");
        }

        return userProfilePhoto;
    }

    /**
     * Use this method to get basic info about a file and prepare it for downloading. For the moment,
     * bots can download files of up to 20MB in size.
     * On success, a File object is returned.
     * The file can then be downloaded via the link https://api.telegram.org/file/bot<token>/<file_path>, where <file_path>
     * is taken from the response. It is guaranteed that the link will be valid for at least 1 hour.
     * When the link expires, a new one can be requested by calling getFile again.
     * <p>
     * Note: This function may not preserve original file name. Mime type of the file and its name (if available) should be
     * saved when the File object is received.
     *
     * @param requestGetFile Request get file
     *
     * @return On success, a File object is returned.
     *
     * @throws IOException
     */
    public File getFile(RequestGetFile requestGetFile) throws IOException {
        String getFileUrl = API_URL + token + "/getFile?file_id=" + requestGetFile.getFile().getFile_id();

        SSLConnection sslConnection = new SSLConnection(getFileUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        File file;
        if ((boolean) jsonResponse.get("ok")) {
            file = (File) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), File.class);
        } else {
            throw new GetFileException("Illegal Response.");
        }

        return file;
    }

    /**
     * Use this method for download file from Telegram server.
     * for get link of file you can use {@link #getFile(RequestGetFile) getFile} method.
     *
     * @param requestDownloadFile Request Download file
     *
     * @throws IOException
     */
    public void downloadFile(RequestDownloadFile requestDownloadFile) throws IOException {
        URL url = new URL(API_DOWNLOAD_FILE_URL + token + "/" + requestDownloadFile.getUri());
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (requestDownloadFile.getName() != null) {
                fileName = requestDownloadFile.getName();
            } else if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = requestDownloadFile.getUri().substring(
                        requestDownloadFile.getUri().lastIndexOf("/") + 1,
                        requestDownloadFile.getUri().length()
                );
            }

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = requestDownloadFile.getPath() + java.io.File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
        } else {
            throw new DownloadFileException("No file to download. May be download link expired. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    /**
     * Use this method to kick a user from a group or a supergroup.
     * In the case of supergroups, the user will not be able to return to the group on their own using invite links, etc.
     * unless unbanned first. The bot must be an administrator in the group for this to work. Returns True on success.
     * <p>
     * Note: This will method only work if the ‘All Members Are Admins’ setting is off in the target group.
     * Otherwise members may only be removed by the group's creator or by the member that added them.
     *
     * @param requestKickChatMember Request kick chat member
     *
     * @return True on success
     *
     * @throws IOException
     */
    public boolean kickChatMember(RequestKickChatMember requestKickChatMember) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/kickChatMember?");

        if (requestKickChatMember.getChat().isValid()) {
            urlBuilder.append("chat_id=").append(requestKickChatMember.getChat().getChatId());
        } else {
            throw new KickChatMemberException("Chat id or chat username is null");
        }

        if (requestKickChatMember.getUser() != null && requestKickChatMember.getUser().getId() != 0) {
            urlBuilder.append("&user_id=").append(requestKickChatMember.getUser().getId());
        } else {
            throw new KickChatMemberException("User id is null");
        }

        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return true;
        } else {
            throw new KickChatMemberException("Illegal Response.");
        }
    }

    /**
     * Use this method for your bot to leave a group, supergroup or channel. Returns True on success.
     *
     * @param requestLeaveChat Request leave chat
     *
     * @return Returns True on success.
     *
     * @throws IOException
     */
    public boolean leaveChat(RequestLeaveChat requestLeaveChat) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/leaveChat?");

        if (requestLeaveChat.getChat().isValid()) {
            urlBuilder.append("chat_id=").append(requestLeaveChat.getChat().getChatId());
        } else {
            throw new LeaveChatException("Chat id or chat username is null");
        }

        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return true;
        } else {
            throw new LeaveChatException("Illegal Response.");
        }
    }

    /**
     * Use this method to unban a previously kicked user in a supergroup.
     * The user will not return to the group automatically, but will be able to join via link, etc.
     * The bot must be an administrator in the group for this to work. Returns True on success.
     *
     * @param requestUnbanChatMember Request unban chat member
     *
     * @return Returns True on success.
     *
     * @throws IOException
     */
    public boolean unbanChatMember(RequestUnbanChatMember requestUnbanChatMember) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/unbanChatMember?");

        if (requestUnbanChatMember.getChat().isValid()) {
            urlBuilder.append("chat_id=").append(requestUnbanChatMember.getChat().getChatId());
        } else {
            throw new UnbanChatMemberException("Chat id or chat username is null");
        }

        if (requestUnbanChatMember.getUser() != null && requestUnbanChatMember.getUser().getId() != 0) {
            urlBuilder.append("&user_id=").append(requestUnbanChatMember.getUser().getId());
        } else {
            throw new UnbanChatMemberException("User id is null");
        }

        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return true;
        } else {
            throw new UnbanChatMemberException("Illegal Response.");
        }
    }

    /**
     * Use this method to get up to date information about the chat (current name of the user for one-on-one conversations,
     * current username of a user, group or channel, etc.). Returns a Chat object on success.
     *
     * @param requestGetChat Request get chat
     *
     * @return Returns a Chat object on success.
     */
    public Chat getChat(RequestGetChat requestGetChat) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/getChat?");

        if (requestGetChat.getChat().isValid()) {
            urlBuilder.append("chat_id=").append(requestGetChat.getChat().getChatId());
        } else {
            throw new GetChatException("Chat id or chat username is null");
        }

        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return (Chat) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Chat.class);
        } else {
            throw new GetChatException("Illegal Response.");
        }
    }

    /**
     * Use this method to get a list of administrators in a chat.
     * On success, returns an Array of ChatMember objects that contains information about all chat administrators except other bots.
     * If the chat is a group or a supergroup and no administrators were appointed, only the creator will be returned.
     *
     * @param requestGetChatAdministrators Request get chat administrators
     *
     * @return returns an Array of ChatMember objects
     *
     * @throws IOException
     */
    public List<ChatMember> getChatAdministrators(RequestGetChatAdministrators requestGetChatAdministrators) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/getChatAdministrators?");

        if (requestGetChatAdministrators.getChat().isValid()) {
            urlBuilder.append("chat_id=").append(requestGetChatAdministrators.getChat().getChatId());
        } else {
            throw new GetChatAdministratorsException("Chat id or chat username is null");
        }

        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        List<ChatMember> listOfAdmins = new ArrayList<>();
        if ((boolean) jsonResponse.get("ok")) {
            jsonResponse.getJSONArray("result").forEach((key) -> {
                ChatMember member = (ChatMember) JsonUtil.fromJsonSerializable(key.toString(), ChatMember.class);
                member.setStatus(ChatMemberStatusEnum.valueOf(((JSONObject) key).get("status").toString().toUpperCase()));
                listOfAdmins.add(member);

            });
        } else {
            throw new GetChatAdministratorsException("Illegal Response.");
        }

        return listOfAdmins;
    }

    /**
     * Use this method to get the number of members in a chat. Returns Int on success.
     *
     * @param requestGetChatMembersCount Request get chat members count
     *
     * @return Returns Int on success.
     *
     * @throws IOException
     */
    public int getChatMembersCount(RequestGetChatMembersCount requestGetChatMembersCount) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/getChatMembersCount?");

        if (requestGetChatMembersCount.getChat().isValid()) {
            urlBuilder.append("chat_id=").append(requestGetChatMembersCount.getChat().getChatId());
        } else {
            throw new GetChatMembersCountException("Chat id and chat username is null");
        }

        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return (int) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), Integer.class);

        } else {
            throw new GetChatMembersCountException("Illegal Response.");
        }

    }

    /**
     * Use this method to get information about a member of a chat. Returns a ChatMember object on success.
     *
     * @param requestGetChatMember Request get chat member
     *
     * @return Returns a ChatMember object on success.
     *
     * @throws IOException
     */
    public ChatMember getChatMember(RequestGetChatMember requestGetChatMember) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/getChatMember?");

        if (requestGetChatMember.getChat().isValid()) {
            urlBuilder.append("chat_id=").append(requestGetChatMember.getChat().getChatId());
        } else {
            throw new GetChatMemberException("Chat id or chat username is null");
        }

        if (requestGetChatMember.getUser() != null && requestGetChatMember.getUser().getId() != 0) {
            urlBuilder.append("&user_id=").append(requestGetChatMember.getUser().getId());
        } else {
            throw new GetChatMemberException("User id is null");
        }

        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            ChatMember chatMember = (ChatMember) JsonUtil.fromJsonSerializable(jsonResponse.get("result").toString(), ChatMember.class);
            chatMember.setStatus(ChatMemberStatusEnum.valueOf(jsonResponse.getJSONObject("result").get("status").toString().toUpperCase()));
            return chatMember;
        } else {
            throw new GetChatMemberException("Illegal Response.");
        }
    }

    /**
     * Use this method to send answers to callback queries sent from inline keyboards. The answer will be displayed
     * to the user as a notification at the top of the chat screen or as an alert. On success, True is returned.
     *
     * @param requestAnswerCallbackQuery Request answer callback query
     *
     * @return On success, True is returned.
     *
     * @throws IOException
     */
    public boolean answerCallbackQuery(RequestAnswerCallbackQuery requestAnswerCallbackQuery) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_URL + token + "/answerCallbackQuery?");

        if (requestAnswerCallbackQuery.getCallbackQuery() == null || requestAnswerCallbackQuery.getCallbackQuery().getId() == null) {
            throw new AnswerCallbackQueryException("CallbackQuery id is null");
        }

        urlBuilder.append("callback_query_id=").append(requestAnswerCallbackQuery.getCallbackQuery().getId());

        if (requestAnswerCallbackQuery.getText() != null) {
            urlBuilder.append("&text=").append(requestAnswerCallbackQuery.getText());
        }

        urlBuilder.append("&show_alert=").append(requestAnswerCallbackQuery.isShowAlert());

        SSLConnection sslConnection = new SSLConnection(urlBuilder.toString());
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        if ((boolean) jsonResponse.get("ok")) {
            return true;
        } else {
            throw new AnswerCallbackQueryException("Illegal Response.");
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

            jsonResponse.getJSONArray("result").forEach((key) -> {
                Message message = (Message) JsonUtil.fromJsonSerializable(((JSONObject) key).get("message").toString(), Message.class);
                message.setUpdateId((Integer) ((JSONObject) key).get("update_id"));
                listOfAllMessage.add(message);
            });
        } else {
            throw new GetUpdateException();
        }

        return listOfAllMessage;
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
    //endregion

    //region Getter and Setter
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

    // Don't set Token directly. Use initialize method instead.
    private static void setToken(String token) {
        Bot.token = token;
    }
    //endregion
}
