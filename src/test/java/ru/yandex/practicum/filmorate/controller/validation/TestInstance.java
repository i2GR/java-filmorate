package ru.yandex.practicum.filmorate.controller.validation;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class TestInstance {

    public static User setDefaultTestUser(int id) {
        User user = new User(id);
        user.setName("userName");
        user.setEmail("email@host.dom");
        user.setLogin("login");
        user.setName("showName");
        user.setBirthday(LocalDate.EPOCH);
        return user;
    }

    public static Film setDefaultTestFilm(int id) {
        Film film = new Film(id);
        film.setName("title");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.EPOCH);
        film.setDuration(100);
        return film;
    }
}
