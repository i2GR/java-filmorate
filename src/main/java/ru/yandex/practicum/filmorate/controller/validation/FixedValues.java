package ru.yandex.practicum.filmorate.controller.validation;

import java.time.LocalDate;

public abstract class Fixed {
    public static final int MOVIE_DESCR_LENGTH = 200;
    public static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895, 12, 28);
}
