package ru.yandex.practicum.filmorate.model;

import lombok.*;

/**
 * класс рейтинга фильма по MPA
 */
@Data
@Builder
public class FilmMpaRating {
    @NonNull
    private final Integer id;

    /**
     * обозначение рейтинга (G, PG  и т.д.)
     */
    private final String name;
}
