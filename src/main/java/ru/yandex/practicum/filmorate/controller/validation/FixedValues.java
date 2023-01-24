package ru.yandex.practicum.filmorate.controller.validation;

import java.time.LocalDate;

/**
 * класс, содержащий конcтанты
 */
public abstract class FixedValues {
    public static final int MAX_MOVIE_DESCR_LENGTH = 200;
    public static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);
}
