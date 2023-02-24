package ru.yandex.practicum.filmorate.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.utils.FilmGenre;
import ru.yandex.practicum.filmorate.utils.FilmRating;
import ru.yandex.practicum.filmorate.validation.AfterCinemaInvention;
import ru.yandex.practicum.filmorate.utils.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO-класс информации о фильме
 * ТЗ-9
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Film extends Entity {

    @NotBlank(message = "Film.name is blank")
    private String name;

    @NotBlank(message = "Film.description is blank")
    @Size(max = Constants.MAX_MOVIE_DESCR_LENGTH, message = "Film.description too long")
    private String description;

    @AfterCinemaInvention
    private LocalDate releaseDate;

    @Positive(message = "Film.duration is zero or negative")
    private int duration;

    //TODO
    //Аннотации (пустой?)
    private List<FilmGenre> genres;

    //TODO
    //Аннотации (пустой?)
    private FilmRating rating;
}