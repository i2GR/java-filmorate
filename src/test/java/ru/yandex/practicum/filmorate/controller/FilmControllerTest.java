package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.activity.likes.InMemoryLikesStorage;
import ru.yandex.practicum.filmorate.storage.activity.likes.LikeStorable;
import ru.yandex.practicum.filmorate.storage.entity.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.entity.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.utils.TestFilmBuilder;

class FilmControllerTest extends ControllerTest<Film>{

    private Film film;

    private FilmControllerTest() {
        super();
        this.storage = new InMemoryFilmStorage();
        this.service = new FilmService((LikeStorable) new InMemoryLikesStorage(), (FilmStorage) storage);
        this.controller = new FilmController(service);
    }

    @BeforeEach
    void setup() {
        TestFilmBuilder builder = new TestFilmBuilder();
        film = builder.defaultFilm().build();
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
    void updateNullMovie() {
        super.updateNull();
    }

    @Test
    void getAllMovies() {
        super.getAll(film);
    }
}