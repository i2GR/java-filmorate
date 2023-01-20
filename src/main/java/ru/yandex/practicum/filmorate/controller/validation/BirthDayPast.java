package ru.yandex.practicum.filmorate.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннтоция для проверки даты рождения юзера
 * имплементация: {@link UserBirthDayValidator}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UserBirthDayValidator.class)
public @interface BirthDayPast {

    String message() default "User.birthdate is in Future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
