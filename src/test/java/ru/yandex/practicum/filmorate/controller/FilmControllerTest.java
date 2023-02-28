package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.BasicModelHandling;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.film.FilmServable;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.activity.likes.InMemoryLikesStorage;
import ru.yandex.practicum.filmorate.storage.entity.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.entity.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.entity.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.utils.TestFilmBuilder;

import java.time.LocalDate;

class FilmControllerTest extends ControllerTest<Film>{

    private Film film;

    private FilmControllerTest() {
        super();
        storage = new InMemoryFilmStorage();
        service = new FilmService(new InMemoryLikesStorage(), (FilmStorage) storage, new InMemoryUserStorage());
        controller = new FilmController((FilmServable) service);
    }

    @BeforeEach
    void setup() {
        //TestFilmBuilder builder = new TestFilmBuilder();
        film = Film.builder()
                .id(1L)
                .name("title")
                .description("Description")
                .releaseDate(LocalDate.EPOCH)
                .duration(100)
                .build();
    }

    @Test
    void addNewMovie() {
        super.addNew(film);
    }

    @Test
    void updateMovie() {
        Film updatedFilm = new TestFilmBuilder()
                .defaultFilm()
                .setName("updated film")
                .build();

        super.update(film, updatedFilm);
    }

    @Test
    void getAllMovies() {
        super.getAll(film);
    }
}