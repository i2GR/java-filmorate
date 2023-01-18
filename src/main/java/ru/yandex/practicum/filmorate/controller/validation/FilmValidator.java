package ru.yandex.practicum.filmorate.controller.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class FilmValidator {

    public static Film validate(Film film) throws ValidationException {
        try {
            nullCheck(film);
            StringValidator.checkEmpty(film.getName(), "Film.name");
            StringValidator.checkLength(film.getDescription().length(), FixedValues.MOVIE_DESCR_LENGTH, "Film.description");
            checkRelease(film);
            checkDuration(film);
        } catch (ValidationException ve) {
            log.warn(ve.getMessage());
            throw ve;
        }
        return film;
    }

    private static boolean checkRelease(Film film) throws ValidationException {
        if (LocalDate.parse(film.getReleaseDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(FixedValues.CINEMA_BIRTHDAY)) {
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

    private static void nullCheck(Film film) throws ValidationException{
        if (film == null) throw new ValidationException("null User received");
    }
}