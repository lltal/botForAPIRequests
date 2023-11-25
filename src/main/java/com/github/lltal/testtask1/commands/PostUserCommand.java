package com.github.lltal.testtask1.commands;

import com.github.lltal.testtask1.dto.PostPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class PostUserCommand implements Command{

    @Autowired
    private RestTemplate restTemplate;
    @Value("${app.uri}")
    private String baseUri;
    private static final String COMMAND_TEXT = "/create_user";

    @Override
    public SendMessage execute(Long who, String... params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        PostPerson requestPerson = new PostPerson();
        requestPerson.setName(params[1] + " " + params[2]);
        HttpEntity<PostPerson> requestEntity = new HttpEntity<>(requestPerson, headers);


        ResponseEntity<PostPerson> responseEntity = restTemplate
                .postForEntity(
                        baseUri,
                        requestEntity,
                        PostPerson.class);
        return SendMessage.builder()
                .chatId(who.toString())
                .text("User id = " + responseEntity.getBody().getId().toString())
                .build();
    }

    @Override
    public String commandType() {
        return COMMAND_TEXT;
    }
}
