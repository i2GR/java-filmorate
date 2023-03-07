package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;

import ru.yandex.practicum.filmorate.service.IdServable;
import ru.yandex.practicum.filmorate.service.InMemoryIdService;
import ru.yandex.practicum.filmorate.service.like.LikeServable;
import ru.yandex.practicum.filmorate.storage.activity.likes.LikeStorable;
import ru.yandex.practicum.filmorate.storage.entity.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * реализация CRUD-функционала в сервис слое для фильмов
 * ТЗ-10
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryFilmService implements FilmServable, LikeServable {

    @NonNull
    private final LikeStorable likeStorage;

    /**
     * хранилище фильмов
     */
    @NonNull
    private final FilmStorage filmStorage;

    @NonNull
    @Qualifier("userDBStorage")
    private final UserStorage userStorage;

    /**
     * сервис-слой обновлению идентификатора
     */
    private final IdServable<Film> idService = new InMemoryIdService<>(0L);

    /**
     * перегружен для проверки наличия идентификатора
     */
    @Override
    public Film create(@NonNull Film film) {
        film = idService.getEntityWithCheckedId(film);
        Optional<Film> optionalFilm = filmStorage.create(film);
        log.info("received data from InMemory {}", optionalFilm.isPresent());
        return optionalFilm.orElseThrow();
    }

    @Override
    public Film readById(@NonNull Long entityId) {
        Optional<Film> optionalFilm = filmStorage.readById(entityId);
        log.info("received data from InMemory {}", optionalFilm.isPresent());
        return optionalFilm.orElseThrow();
    }

    @Override
    public Film update(Film film) {
        Optional<Film> optionalFilm = filmStorage.update(film);
        log.info("updated data in InMemory {}", optionalFilm.isPresent());
        return optionalFilm.orElseThrow();
    }

    @Override
    public List<Film> readAll() {
        return filmStorage.readAll();
    }

    /**
     * реализация с проверкой наличия пользователя и фильма в хранилищах
     * при отсутствии фильма или пользователя будет выброшено исключение
     */
    @Override
    public Like like(@NonNull Long userId, @NonNull Long filmId) {
        userStorage.readById(userId);
        filmStorage.readById(filmId);
        return likeStorage.create(new Like(userId, filmId)).orElseThrow();
    }

    /**
     * реализация с проверкой наличия пользователя и фильма в хранилищах
     * при отсутствии фильма или пользователя будет выброшено исключение
     */
    @Override
    public Like dislike(@NonNull Long userId, @NonNull Long filmId) {
        userStorage.readById(userId);
        filmStorage.readById(filmId);
        return likeStorage.delete(new Like(userId, filmId)).orElseThrow();
    }

    public List<Film> getTopLikedFilms(Integer count) {
        Map<Long, Long> filmIdToLikesNumber = new HashMap<>();
        for (Film film : readAll()) {
            Long id = film.getId();
            long likesNumber = likeStorage.getUsersForFilmById(id).size();
            filmIdToLikesNumber.put(id, likesNumber);
        }
        return filmIdToLikesNumber
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .sorted(Comparator.reverseOrder())
                .limit(count)
                .map(this::readById)
                .collect(Collectors.toList());
    }
}