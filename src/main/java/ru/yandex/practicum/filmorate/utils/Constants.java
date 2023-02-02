package ru.yandex.practicum.filmorate.utils;

import java.time.LocalDate;

/**
 * класс, содержащий конcтанты
 */
public abstract class Constants {
    public static final int MAX_MOVIE_DESCR_LENGTH = 200;
    public static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);

    public static final int MOST_POPULAR_FILMS_AMOUNT = 10;
}
