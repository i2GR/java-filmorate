package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.TestFilmBuilder;

class FilmControllerTest extends ControllerTest<Film>{

    private Film film;

    public FilmControllerTest() {
        super(new FilmController());
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