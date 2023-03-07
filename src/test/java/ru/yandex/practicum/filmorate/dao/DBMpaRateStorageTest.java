package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.FilmMpaRating;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DBMpaRateStorageTest {

    private final DBMpaRateStorage mpaRateStorage;
    private final DBFilmStorage filmStorage;

    private final FilmMpaRating mpa1 = FilmMpaRating.builder().id(1).name("G").build();
    private final FilmMpaRating mpa2 = FilmMpaRating.builder().id(2).name("PG").build();
    private final FilmMpaRating mpa3 = FilmMpaRating.builder().id(3).name("PG-13").build();
    private final FilmMpaRating mpa4 = FilmMpaRating.builder().id(4).name("R").build();
    private final FilmMpaRating mpa5 = FilmMpaRating.builder().id(5).name("NC-17").build();

    @Test
    void readAll() {
        FilmMpaRating[] exp = {mpa1, mpa2, mpa3, mpa4, mpa5};
        FilmMpaRating[] act = mpaRateStorage.readAll().toArray(new FilmMpaRating[]{});

        assertArrayEquals(exp, act);
    }

    @Test
    void readMpaRateById() {

        assertEquals(mpaRateStorage.readMpaRateById(1).orElseThrow(), mpa1);
        assertEquals(mpaRateStorage.readMpaRateById(2).orElseThrow(), mpa2);
        assertEquals(mpaRateStorage.readMpaRateById(4).orElseThrow(), mpa4);
    }

    @Test
    void updateMpaRateFilmById() {
        FilmMpaRating initial = filmStorage.readById(2L).orElseThrow().getMpa();
        mpaRateStorage.updateMpaRateFilmById(2L, mpa5);
        FilmMpaRating updated = filmStorage.readById(2L).orElseThrow().getMpa();

        assertEquals(mpa3, initial);
        assertEquals(mpa5, updated);
    }

    @Test
    void readByFilmId() {
        Film film = Film.builder().name("New mpa film").description("film with new mpa")
                .releaseDate(LocalDate.EPOCH)
                .mpa(mpa5).build();
        Long id = filmStorage.create(film).orElseThrow().getId();
        FilmMpaRating act = mpaRateStorage.readByFilmId(id).orElseThrow();

        assertEquals(mpa5, act);
    }
}