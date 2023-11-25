package com.github.lltal.testtask1;

import com.github.lltal.testtask1.commands.Command;
import com.github.lltal.testtask1.handlers.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "BOT_TOKEN";
    @Autowired
    private CommandHandler commandHandler;

    @Override
    public String getBotUsername() {
        return "Task1Bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv(BOT_TOKEN);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        User user = msg.getFrom();
        Long id = user.getId();

        if (msg.isCommand()){
            String text = msg.getText();
            if(text != null){
                String[] msgParts = text.split(" ");
                Command command = commandHandler.getCommand(msgParts[0]);
                if(command != null){
                    SendMessage sendMessage = null;
                    try {
                        sendMessage = command.execute(id, msgParts);
                    } catch (HttpClientErrorException e){
                        sendMessage = SendMessage.builder()
                                .chatId(id.toString())
                                .text("Illegal command arguments")
                                .build();
                    }

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }
}