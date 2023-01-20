package ru.yandex.practicum.filmorate.controller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * реализация аннтоции для проверки даты выпуска фильма (не ранее даты изобретения)
 */
public class FilmReleaseDateValidator implements ConstraintValidator<AfterCinemaInvention, String> {
    public boolean isValid(String releaseDate, ConstraintValidatorContext cxt) {
        LocalDate filmDate = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return (FixedValues.CINEMA_BIRTHDAY).isBefore(filmDate) ||  (FixedValues.CINEMA_BIRTHDAY).isEqual(filmDate);
    }
}
