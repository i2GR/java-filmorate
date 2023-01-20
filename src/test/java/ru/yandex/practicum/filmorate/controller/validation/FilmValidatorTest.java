package ru.yandex.practicum.filmorate.controller.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.ItemForTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidatorTest {
    private Film film;

    @BeforeEach
    void setupFilm() {
        film = ItemForTest.setDefaultTestFilm(1);
        try {
            assertEquals(film, FilmValidator.validate(film));
        } catch (ValidationException ve){
            fail();
        }
    }

    @Test
    void validateNull() {

        assertThrows(ValidationException.class, () -> FilmValidator.validate(null));
    }

    @Test
    void validateName() {

        film.setName("");

        assertThrows(ValidationException.class, () -> FilmValidator.validate(film));
    }

    @Test
    void validateDescription() {

        film.setDescription("d".repeat(FixedValues.MOVIE_DESCR_LENGTH + 1));

        assertThrows(ValidationException.class, () -> FilmValidator.validate(film));
    }

    @Test
    void validateReleaseDate() {

        film.setReleaseDate(FixedValues.CINEMA_BIRTHDAY.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        assertThrows(ValidationException.class, () -> FilmValidator.validate(film));
    }

    @Test
    void validateDuration() {

        film.setDuration(-1);

        assertThrows(ValidationException.class, () -> FilmValidator.validate(film));
    }
}