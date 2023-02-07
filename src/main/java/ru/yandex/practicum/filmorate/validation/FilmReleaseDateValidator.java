package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.utils.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * реализация аннтоции для проверки даты выпуска фильма (не ранее даты изобретения)
 */
public class FilmReleaseDateValidator implements ConstraintValidator<AfterCinemaInvention, LocalDate> {

    public boolean isValid(LocalDate filmDate, ConstraintValidatorContext cxt) {
        return (Constants.CINEMA_BIRTHDAY).isBefore(filmDate) || (Constants.CINEMA_BIRTHDAY).isEqual(filmDate);
    }
}
