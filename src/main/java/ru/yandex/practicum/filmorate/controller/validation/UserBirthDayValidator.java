package ru.yandex.practicum.filmorate.controller.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * реализация аннтоции для проверки даты врождения (не должна быть в будущем)
 * дата рождения "сегодня" пройдет проверку
 */
public class UserBirthDayValidator implements ConstraintValidator<BirthDayPast, String> {
    public boolean isValid(String birthday, ConstraintValidatorContext cxt) {
        LocalDate birthDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return birthDate.isBefore(LocalDate.now()) ||  (LocalDate.now()).isEqual(birthDate);
    }
}
