package com.github.lltal.testtask1.handlers;

import com.github.lltal.testtask1.commands.Command;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandHandler {

    private final Map<String, Command> commands;

    public CommandHandler() {
        this.commands = new HashMap<>();
    }

    public void registerCommand(String type, Command command){
        commands.put(type, command);
    }

    public Command getCommand(String type){
        return commands.get(type);
    }
}
