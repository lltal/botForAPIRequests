package com.github.lltal.testtask1.commands;

import com.github.lltal.testtask1.dto.PersonItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class GetUserCommand implements Command{

    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.uri}")
    private String baseUri;
    private static final String COMMAND_TEXT = "/get_user";
    private static final String WRONG_MESSAGE_FORMAT = "Неправильный формат сообщения.\n Введите сообщение в формате '/get_user <ID>'";

    @Override
    public SendMessage execute(Long who, String... params) {
        if (params.length > 1) {
            ResponseEntity<PersonItem> entity = restTemplate
                    .getForEntity(
                            baseUri + "/{id}",
                            PersonItem.class,
                            params[1]);
            return SendMessage.builder()
                    .chatId(who.toString())
                    .text(entity.getBody().getPerson().toString())
                    .build();
        } else {
            return SendMessage.builder()
                    .chatId(who.toString())
                    .text(WRONG_MESSAGE_FORMAT)
                    .build();
        }
    }

    @Override
    public String commandType() {
        return COMMAND_TEXT;
    }
}
