package ru.yandex.practicum.filmorate.controller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * реализация аннтоции для проверки даты выпуска фильма (не ранее даты изобретения)
 */
public class FilmReleaseDateValidator implements ConstraintValidator<AfterCinemaInvention, LocalDate> {

    public boolean isValid(LocalDate filmDate, ConstraintValidatorContext cxt) {
        return (FixedValues.CINEMA_BIRTHDAY).isBefore(filmDate) || (FixedValues.CINEMA_BIRTHDAY).isEqual(filmDate);
    }
}
