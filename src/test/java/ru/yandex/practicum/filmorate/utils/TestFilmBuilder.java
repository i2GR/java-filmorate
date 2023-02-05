package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class TestFilmBuilder {

    private final Film film = new Film();

    public TestFilmBuilder defaultFilm () {
        setId(1);
        setName("title");
        setDescription("Description");
        setReleaseDate(LocalDate.EPOCH);
        setDuration(100);
        return this;
    }

    public TestFilmBuilder setId(int id) {
        film.setId(id);
        return this;
    }

    public TestFilmBuilder setName(String name) {
       film.setName(name);
       return this;
    }

    public TestFilmBuilder setDescription(String description) {
        film.setDescription(description);
        return this;
    }

    public TestFilmBuilder setDuration(int duration) {
        film.setDuration(duration);
        return this;
    }

    public TestFilmBuilder setReleaseDate(LocalDate releaseDate) {
        film.setReleaseDate(releaseDate);
        return this;
    }

    public Film build() {
        return film;
    }
}
