package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.controller.validation.AfterCinemaInvention;
import javax.validation.constraints.*;

@Data
public class Film {
    @EqualsAndHashCode.Exclude
    private int id;

    @NotBlank(message = "Film.name is blank")
    private String name;

    @NotBlank(message = "Film.description is blank")
    @Size(max = 200, message = "Film.description too long")
    private String description;

    @AfterCinemaInvention
    private String releaseDate;

    @Positive(message = "Film.duration is zero or negative")
    private int duration;

}
