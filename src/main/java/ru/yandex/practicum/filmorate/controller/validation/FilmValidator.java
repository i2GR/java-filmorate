package ru.yandex.practicum.filmorate.controller.validation;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * валидатор фильма согласно первой части ТЗ (без учета доп.задания)
 * @Note осталось применение только в тестах
 */
public class FilmValidator {

    /**
     * вадидация фильма
     * @param film фильм
     * @return экземпляр фильма
     * @throws ValidationException пользовательствое иисключение согласно ТЗ
     */
    public static Film validate(Film film) throws ValidationException {
        if (film == null) throw new ValidationException("null Film received");
        StringValidator.checkEmpty(film.getName(), "Film.name");
        StringValidator.checkLength(film.getDescription().length(), FixedValues.MOVIE_DESCR_LENGTH, "Film.description");
        checkRelease(film);
        checkDuration(film);
        return film;
    }

    private static void checkRelease(Film film) throws ValidationException {
        if (LocalDate.parse(film.getReleaseDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isBefore(FixedValues.CINEMA_BIRTHDAY)) {
            throw new ValidationException("film.releaseDate is before cinema era");
        }
    }

    private static void  checkDuration(Film film) throws ValidationException {
        if (film.getDuration() <= 0) {
            throw new ValidationException("film.duration is negative or zero");
        }
    }

}