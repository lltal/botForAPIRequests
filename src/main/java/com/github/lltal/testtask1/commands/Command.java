package com.github.lltal.testtask1.commands;

import com.github.lltal.testtask1.handlers.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Command {
    SendMessage execute(Long who, String... params);

    @Autowired
    default void regMe(CommandHandler handler){
        handler.registerCommand(commandType(), this);
    }
    String commandType();
}
