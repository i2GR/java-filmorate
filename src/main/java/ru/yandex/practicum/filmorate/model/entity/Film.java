package ru.yandex.practicum.filmorate.model.entity;

import lombok.*;
import ru.yandex.practicum.filmorate.model.FilmMpaRating;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.validation.AfterCinemaInvention;
import ru.yandex.practicum.filmorate.utils.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO-класс информации о фильме
 * ТЗ-9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Film extends Entity {

    private Long id;

    @NotBlank(message = "Film.name is blank")
    private String name;

    @NotBlank(message = "Film.description is blank")
    @Size(max = Constants.MAX_MOVIE_DESCR_LENGTH, message = "Film.description too long")
    private String description;

    @AfterCinemaInvention
    private LocalDate releaseDate;

    @Positive(message = "Film.duration is zero or negative")
    private int duration;

    /**
     * по результатам мозгового штурма: рейтинг фильма на основе лайков
     *
     * @implNote ???<p>необходимая реализация неизвестна: количество ли лайков или место ли в рейтинге на его основе<p>???
     * <p>ТЗ-11
     */
    @Builder.Default
    private Integer rate = 0;

    @Builder.Default
    private List<Genre> genres = new ArrayList<>();

    private FilmMpaRating mpa;
}