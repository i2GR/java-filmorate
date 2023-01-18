package ru.yandex.practicum.filmorate.controller.validation;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestInstance {

    public static User setDefaultTestUser(int id) {
        User user = new User();
        user.setId(id);
        user.setName("userName");
        user.setEmail("email@host.dom");
        user.setLogin("login");
        user.setName("showName");
        user.setBirthday(LocalDate.EPOCH.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return user;
    }

    public static Film setDefaultTestFilm(int id) {
        Film film = new Film();
        film.setId(id);
        film.setName("title");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.EPOCH.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        film.setDuration(100);
        return film;
    }
}
