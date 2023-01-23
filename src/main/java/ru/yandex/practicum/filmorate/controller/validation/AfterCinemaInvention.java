package ru.yandex.practicum.filmorate.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннтоция для проверки даты выпуска фильма
 * имплементация: {@link FilmReleaseDateValidator}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = FilmReleaseDateValidator.class)
public @interface AfterCinemaInvention {
    String message() default "Film.releaseDate before cinema era";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

