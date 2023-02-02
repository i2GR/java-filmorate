package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.FileSystemXmlApplicationContext;


@SpringBootApplication
public class FilmorateApplication {

	public static void main(String[] args) {
		//ApplicationContext context = new FileSystemXmlApplicationContext("H:/NewSkills/Java/YP/sp9/final/filmorate/src/main/resources/applicationContext.xml");

		SpringApplication.run(FilmorateApplication.class, args);
	}
}
