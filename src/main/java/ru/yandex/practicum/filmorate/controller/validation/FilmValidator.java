package ru.yandex.practicum.filmorate.controller.validation;

import ru.yandex.practicum.filmorate.model.Film;

public class FilmValidator {

    public static Film validate(Film film) throws ValidationException {
        StringValidator.checkEmpty(film.getName(), "Film.name");
        StringValidator.checkLength(film.getDescription().length(), FixedValues.MOVIE_DESCR_LENGTH, "Film.description");
        checkRelease(film);
        checkDuration(film);
        return film;
    }

    private static void checkRelease(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(FixedValues.CINEMA_BIRTHDAY)) {
            throw new ValidationException("movie release date before cinema era");
        }
    }

    private static void checkDuration(Film film) throws ValidationException {
        if (film.getDuration().isNegative()) {
            throw new ValidationException("negative movie duration");
        }
    }
}