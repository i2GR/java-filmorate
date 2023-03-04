package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.FilmMpaRating;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DBFilmStorageTest {

    private final DBFilmStorage filmStorage;

    private final JdbcTemplate jdbcTemplate;
    Film film3, film4, film5;

    @BeforeEach
    void reinitialiseFilms() {
        jdbcTemplate.update("DELETE FROM films WHERE id > 2");
    }

    @Test
    void create() {
        film3 = Film.builder().name("film3").description("super").releaseDate(LocalDate.EPOCH)
                .duration(130).mpa(FilmMpaRating.builder().id(1).build())
                .build();
        Optional<Film> optFilm = filmStorage.create(film3);

        assertThat(optFilm)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasNoNullFieldsOrProperties().hasFieldOrPropertyWithValue("id", 3L));

    }

    @Test
    void readById() {
        Optional<Film> optFilm1 = filmStorage.readById(1L);

        assertThat(optFilm1)
                .isPresent()
                .hasValueSatisfying(film -> assertThat(film)
                        .hasFieldOrPropertyWithValue("id", 1L)
                        .hasFieldOrPropertyWithValue("name", "nisi eiusmod")
                        .hasFieldOrPropertyWithValue("description", "adipisicing")
                        .hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(1967, 3, 25))
                        .hasFieldOrPropertyWithValue("duration", 100));
    }

    @Test
    public void readByBadId() {
        assertTrue(filmStorage.readById(-1L).isEmpty());
    }

    @Test
    void update() {
        film4 = Film.builder().name("New film 4").description("film4").releaseDate(LocalDate.EPOCH)
                .duration(100).mpa(FilmMpaRating.builder().id(2).build())
                .genres(List.of(Genre.builder().id(1).build()))
                .build();
        film4 = filmStorage.create(film4).orElseThrow();
        film4.setDuration(110);
        film4.setDescription("film4 Updated description");
        film4.setMpa(FilmMpaRating.builder().id(1).build());
        Long id = film4.getId();

        Optional<Film> updatedOptFilm4 = filmStorage.update(film4);

        assertThat(updatedOptFilm4)
                .isPresent()
                .hasValueSatisfying(film -> assertThat(film)
                        .hasFieldOrPropertyWithValue("id", id)
                        .hasFieldOrPropertyWithValue("name", "New film 4")
                        .hasFieldOrPropertyWithValue("description", "film4 Updated description")
                        .hasFieldOrPropertyWithValue("releaseDate", LocalDate.EPOCH)
                        .hasFieldOrPropertyWithValue("duration", 110)
                        .hasFieldOrPropertyWithValue("mpa", FilmMpaRating.builder().id(1).build()));
    }

    @Test
    void readAll() {
        List<Film> films = filmStorage.readAll();

        assertEquals(2, films.size());
        assertThat(films.get(0))
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "nisi eiusmod")
                .hasFieldOrPropertyWithValue("description", "adipisicing")
                .hasFieldOrPropertyWithValue("releaseDate", LocalDate.of(1967, 3, 25))
                .hasFieldOrPropertyWithValue("duration", 100);
    }

    @Test
    void delete() {
        film5 = Film.builder().name("New").description("film5").releaseDate(LocalDate.EPOCH)
                .duration(99).rate(1)
                .mpa(FilmMpaRating.builder().id(1).build()).build();
        Long filmIdAdded = filmStorage.create(film5).orElseThrow().getId();
        int exp = filmStorage.readAll().size();
        filmStorage.delete(filmIdAdded);

        int act = filmStorage.readAll().size();

        assertEquals(act + 1, exp);
    }
}

