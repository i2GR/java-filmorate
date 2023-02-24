package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.IdServable;
import ru.yandex.practicum.filmorate.service.IdService;
import ru.yandex.practicum.filmorate.service.like.LikeServable;
import ru.yandex.practicum.filmorate.storage.activity.likes.LikeStorable;
import ru.yandex.practicum.filmorate.storage.entity.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * реализация CRUD-функционала в сервис слое для фильмов
 * ТЗ-10
 */
@Service
@RequiredArgsConstructor
public class FilmService implements FilmServable, LikeServable {

    @NonNull
    private final LikeStorable likeStorage;

    /**
     * хранилище фильмов
     */
    @NonNull
    private final FilmStorage filmStorage;

    @NonNull
    private final UserStorage userStorage;

    /**
     * сервис-слой обновлению идентификатора
     */
    private final IdServable<Film> idService = new IdService<>(0L);

    /**
     * перегружен для проверки наличия идентификатора
     */
    @Override
    public Film create (@NonNull Film film) {
        film = idService.getEntityWithCheckedId(film);
        return filmStorage.create(film);
    }

    @Override
    public Film readById(@NonNull Long entityId){
        return filmStorage.readById(entityId);
    }

    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
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
        return likeStorage.create(new Like(userId, filmId));
    }

    /**
     * реализация с проверкой наличия пользователя и фильма в хранилищах
     * при отсутствии фильма или пользователя будет выброшено исключение
     */
    @Override
    public Like dislike(@NonNull Long userId, @NonNull Long filmId) {
        userStorage.readById(userId);
        filmStorage.readById(filmId);
        return likeStorage.delete(new Like(userId, filmId));
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