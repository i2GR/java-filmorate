package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DBGenreStorageTest {

    private final DBGenreStorage genreStorage;
    private final DBFilmStorage filmStorage;
    private final Genre genre1 = Genre.builder().id(1).name("Комедия").build();
    private final Genre genre2 = Genre.builder().id(2).name("Драма").build();
    private final Genre genre3 = Genre.builder().id(3).name("Мультфильм").build();
    private final Genre genre4 = Genre.builder().id(4).name("Триллер").build();
    private final Genre genre5 = Genre.builder().id(5).name("Документальный").build();
    private final Genre genre6 = Genre.builder().id(6).name("Боевик").build();

    @Test
    void readAll() {
        Genre[] exp = {genre1, genre2, genre3, genre4, genre5, genre6};
        Genre[] act = genreStorage.readAll().toArray(new Genre[]{});

        assertArrayEquals(exp, act);
    }

    @Test
    void readGenreById() {

        assertEquals(genreStorage.readGenreById(1).orElseThrow(), genre1);
        assertEquals(genreStorage.readGenreById(5).orElseThrow(), genre5);
        assertEquals(genreStorage.readGenreById(6).orElseThrow(), genre6);

    }

    @Test
    void updateGenresForFilm() {
        Genre[] initial = genreStorage.readByFilmId(1L).toArray(new Genre[]{});
        Film film = filmStorage.readById(1L).orElseThrow();

        film.setGenres(List.of(genre6));
        genreStorage.updateGenresForFilm(film);
        Genre[] updated = genreStorage.readByFilmId(1L).toArray(new Genre[]{});

        assertArrayEquals(new Genre[]{genre1, genre3}, initial);
        assertArrayEquals(new Genre[]{genre6}, updated);

        film.setGenres(List.of(initial));
        genreStorage.updateGenresForFilm(film);
    }

    @Test
    void readByFilmId() {
        Genre[] act = genreStorage.readByFilmId(2L).toArray(new Genre[]{});

        assertArrayEquals(new Genre[]{genre5}, act);
    }
}