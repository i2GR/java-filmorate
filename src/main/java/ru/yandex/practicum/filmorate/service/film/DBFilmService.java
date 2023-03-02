package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.entity.Film;
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

    /**
     * хранилище в БД
     */
    @NonNull
    @Qualifier("filmDBStorage")
    private final FilmStorage filmStorage;

    /**
     * перегружен для проверки наличия идентификатора
     */
    @Override
    public Film create (@NonNull Film film) {
        Optional<Film> optionalFilm = filmStorage.create(film);
        log.info("received response from DB {} when record:", optionalFilm.isPresent());
        return optionalFilm.orElseThrow();
    }

    @Override
    public Film readById(@NonNull Long entityId){
        Optional<Film> optionalFilm = filmStorage.readById(entityId);
        log.info("received data from DB {}", optionalFilm.isPresent());
        return optionalFilm.orElseThrow();
    }

    @Override
    public Film update(Film film) {
        Optional<Film> optionalFilm = filmStorage.update(film);
        log.info("received data from DB {}", optionalFilm.isPresent());
        return optionalFilm.orElseThrow();
    }

    @Override
    public List<Film> readAll() {
        List<Film> filmList = filmStorage.readAll();
        log.info("received data from DB {}", filmList.size());
        return filmList;
    }
}