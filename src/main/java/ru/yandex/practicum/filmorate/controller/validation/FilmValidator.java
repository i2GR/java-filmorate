package ru.yandex.practicum.filmorate.controller.validation;

import ru.yandex.practicum.filmorate.model.Film;

public class FilmValidator {

    public static Film validate(Film film) throws ValidationException {
        if (film == null) throw new ValidationException("null object received");
        StringValidator.checkEmpty(film.getName(), "Film.name");
        StringValidator.checkLength(film.getDescription().length(), FixedValues.MOVIE_DESCR_LENGTH, "Film.description");
        checkRelease(film);
        checkDuration(film);
        return film;
    }

    private static boolean checkRelease(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(FixedValues.CINEMA_BIRTHDAY)) {
            throw new ValidationException("movie release date before cinema era");
        }
        return true;
    }

    private static boolean checkDuration(Film film) throws ValidationException {
        if (film.getDuration() <= 0) {
            throw new ValidationException("zero or negative movie duration");
        }
        return true;
    }
}