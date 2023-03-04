package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.genre.GenreServable;
import ru.yandex.practicum.filmorate.storage.entity.film.FilmStorage;

import java.util.*;

/**
 * реализация CRUD-функционала в сервис слое для фильмов
 * хранение данных в БД
 * ТЗ-11
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class DBFilmService implements FilmServable {

    @NonNull
    @Qualifier("filmDBStorage")
    private final FilmStorage filmStorage;

    @NonNull
    private final GenreServable genreService;

    /**
     * перегружен для проверки наличия идентификатора
     */
    @Override
    public Film create(@NonNull Film film) {
        Optional<Film> optionalFilm = filmStorage.create(film);
        log.info("received response from DB {} when record:", optionalFilm.isPresent());
        int affectedRows = genreService.updateGenreForFilm(film);
        log.info("film-Genres recorded to DB: {}", affectedRows);
        return optionalFilm.orElseThrow();
    }

    @Override
    public Film readById(@NonNull Long entityId) {
        List<Genre> genres = genreService.getGenresForFilmById(entityId);
        log.info("received film-genres-data from DB {}", genres.size());
        Optional<Film> optionalFilm = filmStorage.readById(entityId);
        log.info("received film-data from DB {}", optionalFilm.isPresent());
        Film film = optionalFilm.orElseThrow();
        film.setGenres(genres);
        log.info("Genres attached to film");
        return film;
    }

    @Override
    public Film update(Film film) {
        genreService.updateGenreForFilm(film);
        Optional<Film> optionalFilm = filmStorage.update(film);
        log.info("received genres data from DB for filmId {}, {}", film.getId(), optionalFilm.isPresent());
        List<Genre> genres = genreService.getGenresForFilmById(film.getId());
        log.info("received film-genres-data from DB {}", genres.size());
        Film returnedFilm = optionalFilm.orElseThrow();
        returnedFilm.setGenres(genres);
        log.info("Genres attached to film");
        return returnedFilm;
    }

    @Override
    public List<Film> readAll() {
        List<Film> filmList = filmStorage.readAll();
        log.info("received films data from DB {}", filmList.size());
        for (Film film : filmList) {
            film.setGenres(genreService.getGenresForFilmById(film.getId()));
        }
        return filmList;
    }
}