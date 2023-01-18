package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.validation.TestInstance;
import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private final Film film = TestInstance.setDefaultTestFilm(1);
    FilmController fc = new FilmController();

    @Test
    void addNewMovie() throws ValidationException {
        assertEquals(film,fc.addNewMovie(film));
        assertEquals(1, fc.getAllMovies().size());
        assertThrows(ValidationException.class, () -> fc.addNewMovie(film));
        assertThrows(ValidationException.class, () -> fc.addNewMovie(null));
    }

    @Test
    void updateMovie() throws ValidationException {
        fc.addNewMovie(film);
        film.setName("newName");

        assertEquals("newName", fc.updateMovie(film).getName());
        assertThrows(ValidationException.class, () -> fc.updateMovie(null));
    }

    @Test
    void getAllMovies() throws ValidationException {
        fc.addNewMovie(film);
        Film film2 = TestInstance.setDefaultTestFilm(2);
        film2.setName("newName");
        fc.addNewMovie(film2);

        Film[] expected = {film, film2};
        Film[] actual = fc.getAllMovies().toArray(new Film[]{});

        assertArrayEquals(expected, actual);
    }
}