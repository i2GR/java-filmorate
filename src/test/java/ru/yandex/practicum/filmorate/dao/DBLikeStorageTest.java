package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DBLikeStorageTest {

    private final DBLikeStorage likeStorage;
    private final JdbcTemplate jdbcTemplate;

    @BeforeEach
    void reinitialiseLikes() {
        jdbcTemplate.update("DELETE FROM user_liked_film WHERE film_id = 2;");
    }

    @Test
    void create() {
        Optional<Like> addedLike = likeStorage.create(new Like(1L, 2L));

        assertThat(addedLike)
                .isPresent()
                .hasValueSatisfying(like ->
                        assertThat(like).hasNoNullFieldsOrProperties()
                                .hasFieldOrPropertyWithValue("userId", 1L)
                                .hasFieldOrPropertyWithValue("filmId", 2L));
    }

    @Test
    void read() {
        assertTrue(likeStorage.read(new Like(1L, 1L)));
    }

    @Test
    void delete() {
        Like like = new Like(2L, 2L);
        boolean initiallyPresent = likeStorage.read(like);
        likeStorage.create(like);
        boolean addedPresent = likeStorage.read(like);
        likeStorage.delete(like);
        boolean deletedPresent = likeStorage.read(like);

        assertFalse(initiallyPresent);
        assertTrue(addedPresent);
        assertFalse(deletedPresent);
    }

    @Test
    void getUsersForFilmById() {
        Long[] usersForFilm1 = likeStorage.getUsersForFilmById(1L).toArray(new Long[]{});
        Long[] usersForFilm2 = likeStorage.getUsersForFilmById(2L).toArray(new Long[]{});

        assertEquals(2, usersForFilm1.length);
        assertEquals(0, usersForFilm2.length);
        assertArrayEquals(new Long[]{1L, 2L}, usersForFilm1);
    }

    @Test
    void getTopFilms() {
        List<Film> top0 = likeStorage.getTopFilms(0);
        List<Film> top10actual2 = likeStorage.getTopFilms(10);

        assertEquals(0, top0.size());
        assertEquals(2, top10actual2.size());
        assertEquals(1, top10actual2.get(0).getId());
        assertEquals(2, top10actual2.get(1).getId());
    }
}