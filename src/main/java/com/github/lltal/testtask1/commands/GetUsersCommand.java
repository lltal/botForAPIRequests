package com.github.lltal.testtask1.commands;

import com.github.lltal.testtask1.dto.PersonList;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class GetUsersCommand implements Command{

    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.uri}")
    private String baseUri;
    private static final String COMMAND_TEXT = "/get_users";
    private static final String NO_USERS_IN_PAGE = "no users found in page=%s";

    @Override
    public SendMessage execute(Long who, String... params) {
        String pageId = params.length > 1 ? params[1] : String.valueOf(1);
        ResponseEntity<PersonList> entity = restTemplate
                .getForEntity(
                        baseUri + "?page={id}",
                        PersonList.class,
                        pageId);

        PersonList body = entity.getBody();

        StringBuilder sb = new StringBuilder();
        body.getData().forEach(p -> {
            sb.append(p.toString()).append("\n").append("\n");
        });

        String text = sb.length() > 2 ?
                sb.substring(0, sb.length() - 2) :
                String.format(NO_USERS_IN_PAGE, pageId);

        return SendMessage.builder()
                .chatId(who.toString())
                .text(text)
                .build();
    }

    @Override
    public String commandType() {
        return COMMAND_TEXT;
    }
}
