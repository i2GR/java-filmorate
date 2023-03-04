package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.utils.TestFilmBuilder;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryIdServiceTest {

    InMemoryIdService<Film> idFilmService;

    private final Long lastID = 1L;

    @Test
    void testServiceOnFilmWithId() {
        idFilmService= new InMemoryIdService<>(lastID);
        Film expected = new TestFilmBuilder()
                .defaultFilm()
                .setId(2131L)
                .build();

        Film actual = idFilmService.getEntityWithCheckedId(expected);

        assertEquals(expected.getId().longValue(), actual.getId().longValue());
    }

    @Test
    void testServiceOnFilmNullId() {
        idFilmService= new InMemoryIdService<>(lastID);
        Film expected = new TestFilmBuilder()
                .defaultFilm()
                .setId(null)
                .build();

        Film actual = idFilmService.getEntityWithCheckedId(expected);

        assertEquals(lastID + 1L, actual.getId().longValue());
    }
}