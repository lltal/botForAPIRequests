package com.github.lltal.testtask1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class TestTaskApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(TestTaskApplication.class, args);
	}

}
