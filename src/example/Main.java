package example;

import api.core.Bot;
import api.entity.*;

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
        buttonGroup1.add(button3);
        buttonGroup1.add(button4);

        List<List<KeyboardButton>> buttonList = new ArrayList<>();
        buttonList.add(buttonGroup1);
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
        requestUploadPhoto.setInputFile(new InputFile("/home/gladiator/Desktop/1.jpg"));
        requestUploadPhoto.setReplyMarkup(replyKeyboardMarkup);
        Message responseUploadPhoto = Bot.getInstance().sendPhoto(requestUploadPhoto);*/

        // Resend photo by file_id
        /*RequestSendPhoto requestSendPhoto = new RequestSendPhoto(chat, responseUploadPhoto.getPhoto().get(0));
        requestSendPhoto.setReplyMarkup(replyKeyboardMarkup);
        Bot.getInstance().sendPhoto(requestSendPhoto);*/




    }
}
