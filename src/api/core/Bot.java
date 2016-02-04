package api.core;

import api.entity.Message;
import api.entity.User;
import api.exception.ForwardMessageException;
import api.exception.SendLocationException;
import api.exception.SendMessageException;
import api.json.JSONObject;
import api.net.SSLConnection;
import api.requestObject.RequestForwardMessage;
import api.requestObject.RequestSendLocation;
import api.requestObject.RequestSendMessage;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot {
    private String id;
    private String username;
    private String token;

    private static String apiUrl = "https://api.telegram.org/bot";

    public Bot(String token) {
        this.token = token;
    }

    public User getMe() throws IOException {
        String getMeUrl = apiUrl + this.token + "/getMe";
        SSLConnection sslConnection = new SSLConnection(getMeUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        User user = new User();
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();
            user = gson.fromJson(jsonResponse.get("result").toString(), User.class);
        } else {
            //TODO: write exception for error in response from telegram;
        }

        return user;
    }

    public List<Message> getUpdates() throws IOException {
        String updateUrl = apiUrl + this.token + "/getUpdates";
        SSLConnection sslConnection = new SSLConnection(updateUrl);
        JSONObject jsonResponse = sslConnection.getSSLConnection();

        List<Message> listOfAllMessage = new ArrayList<Message>();
        if ((boolean) jsonResponse.get("ok")) {
            Gson gson = new Gson();

            for (int counter = 0; counter < jsonResponse.getJSONArray("result").length(); counter++) {
                Message message = gson.fromJson(jsonResponse.getJSONArray("result").getJSONObject(counter).get("message").toString()
                        , Message.class);
                listOfAllMessage.add(message);
            }
        } else {
            //TODO: write exception for error in response from telegram;
        }

        return listOfAllMessage;
    }

    public void forwardMessage(RequestForwardMessage requestForwardMessage) throws IOException {
        String forwardUrl = apiUrl + this.token + "/forwardMessage?chat_id=" + requestForwardMessage.getChat().getId()
                + "&from_chat_id=" + requestForwardMessage.getMessage().getChat().getId()
                + "&message_id=" + requestForwardMessage.getMessage().getMessageId();
        SSLConnection sslConnection = new SSLConnection(forwardUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new ForwardMessageException(e.getMessage());
        }

    }

    public void sendMessage(RequestSendMessage requestSendMessage) throws IOException {
        String chatId;
        if (requestSendMessage.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendMessage.getChat().getId());
        } else {
            chatId = "@" + requestSendMessage.getChat().getUsername();
        }

        String sendMessageUrl = apiUrl + this.token + "/sendMessage?chat_id=" + chatId
                + "&text=" + requestSendMessage.getText();

        if (requestSendMessage.getParseMode() != null) {
            sendMessageUrl = sendMessageUrl + "&parse_mode=" + requestSendMessage.getParseMode();
        }
        if (requestSendMessage.getReplyToMessageId() != 0) {
            sendMessageUrl = sendMessageUrl + "&reply_to_message_id=" + requestSendMessage.getReplyToMessageId();
        }
        if (requestSendMessage.getDisableWebPagePreview()) {
            sendMessageUrl = sendMessageUrl + "&disable_web_page_preview=" + requestSendMessage.getDisableWebPagePreview();
        }
        //TODO: add replyMarkup support;

        SSLConnection sslConnection = new SSLConnection(sendMessageUrl);
        try {
            JSONObject jsonResponse = sslConnection.getSSLConnection();
        } catch (Exception e) {
            throw new SendMessageException(e.getMessage());
        }
    }

    public void sendLocation(RequestSendLocation requestSendLocation) {
        String chatId;
        if (requestSendLocation.getChat().getId() != 0) {
            chatId = String.valueOf(requestSendLocation.getChat().getId());
        } else {
            chatId = "@" + requestSendLocation.getChat().getUsername();
        }

        String sendLocationUrl = apiUrl + this.token + "/sendLocation?chat_id=" + chatId
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
