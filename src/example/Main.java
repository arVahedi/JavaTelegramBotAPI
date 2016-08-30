package example;

import api.core.Bot;
import api.entity.*;
import api.requestobject.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gladiator on 8/29/16.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Bot.setToken("116099220:AAG2g_GfyUSLBdO9b8vT48TnNPnQl6z6DOg");
        Chat chat = new Chat(74619619);

        // Make replyKeyboard
        KeyboardButton button1 = new KeyboardButton("btn 1");
        KeyboardButton button2 = new KeyboardButton("btn 2");
        KeyboardButton button3 = new KeyboardButton("btn 3");
        KeyboardButton button4 = new KeyboardButton("btn 4");
        List<KeyboardButton> buttonGroup1 = new ArrayList<>();
        buttonGroup1.add(button1);
        buttonGroup1.add(button2);
        List<KeyboardButton> buttonGroup2 = new ArrayList<>();
        buttonGroup2.add(button3);
        buttonGroup2.add(button4);

        List<List<KeyboardButton>> buttonList = new ArrayList<>();
        buttonList.add(buttonGroup1);
        buttonList.add(buttonGroup2);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(buttonList);


        // Get updates
        /*List<Message> messageList = Bot.getInstance().getUpdates(new RequestGetUpdate());
        messageList.forEach((message -> {
            System.out.println(message.getChat().getChatId());
        }));*/

        // Send Message
        /*RequestSendMessage requestSendMessage = new RequestSendMessage(new Chat(74619619), "Amnafzar Co.");
        Bot.getInstance().sendMessage(requestSendMessage);*/

        // Upload photo
        /*RequestSendPhoto requestUploadPhoto = new RequestSendPhoto();
        requestUploadPhoto.setChat(chat);
        requestUploadPhoto.setInputFile(new InputFile("/Users/Gladiator/Desktop/1.jpg"));
        requestUploadPhoto.setReplyMarkup(replyKeyboardMarkup);
        requestUploadPhoto.setCaption("Hacked stamp.");
        Message responseUploadPhoto = Bot.getInstance().sendPhoto(requestUploadPhoto);*/

        // Resend photo by file_id
        /*RequestSendPhoto requestSendPhoto = new RequestSendPhoto(chat, responseUploadPhoto.getPhoto().get(0));
        requestSendPhoto.setReplyMarkup(replyKeyboardMarkup);
        Bot.getInstance().sendPhoto(requestSendPhoto);*/

        // Send audio file
        /*RequestSendAudio requestSendAudio = new RequestSendAudio();
        requestSendAudio.setChat(chat);
        requestSendAudio.setDisableNotification(false);
        requestSendAudio.setInputFile(new InputFile("/Users/Gladiator/Desktop/1.mp3"));
        requestSendAudio.setReplyMarkup(replyKeyboardMarkup);
        Message responseUploadAudio = Bot.getInstance().sendAudio(requestSendAudio);*/

        // Send document
        /*RequestSendDocument requestSendDocument = new RequestSendDocument();
        requestSendDocument.setChat(chat);
        requestSendDocument.setInputFile(new InputFile("/Users/Gladiator/Desktop/url.txt"));
        requestSendDocument.setReplyMarkup(replyKeyboardMarkup);
        Bot.getInstance().sendDocument(requestSendDocument);*/

        // Send video
        /*RequestSendVideo requestSendVideo = new RequestSendVideo();
        requestSendVideo.setChat(chat);
        requestSendVideo.setInputFile(new InputFile("/Users/Gladiator/Desktop/1.mp4"));
        requestSendVideo.setCaption("caption of file.");
        Bot.getInstance().sendVideo(requestSendVideo);*/

        // Send location
        /*RequestSendLocation requestSendLocation = new RequestSendLocation(chat, new Location(51.3253434, 35.7346714));
        Bot.getInstance().sendLocation(requestSendLocation);*/

        // Send venue
        /*RequestSendVenue requestSendVenue = new RequestSendVenue(chat, new Venue(new Location(51.3253434, 35.7346714), "Title venue test", "Sadegiyeh sq, abozar, payambaran hostpiral, room 307"));
        Bot.getInstance().sendVenue(requestSendVenue);*/

    }
}
