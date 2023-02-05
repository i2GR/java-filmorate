package ru.yandex.practicum.filmorate.service.film;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.BasicEntityService;
import ru.yandex.practicum.filmorate.service.like.LikeServable;
import ru.yandex.practicum.filmorate.storage.activity.likes.LikeStorable;
import ru.yandex.practicum.filmorate.storage.entity.film.FilmStorage;
import ru.yandex.practicum.filmorate.utils.EntityType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * реализация CRUD-функционала в сервис слое
 * ТЗ-10
 */
@Service
public class FilmService extends BasicEntityService<Film> implements FilmServable, LikeServable {

    @Autowired
    private final LikeStorable likeStorage;

    public FilmService(@NonNull LikeStorable likeStorage, @NonNull FilmStorage filmStorage) {
        super(EntityType.FILM.val(), filmStorage);
        this.likeStorage = likeStorage;
    }

    @Override
    public Like like(@NonNull Long userId, @NonNull Long filmId) {
        return likeStorage.create(new Like(userId, filmId));
    }

    @Override
    public Like dislike(@NonNull Long userId, @NonNull Long filmId) {
        return likeStorage.delete(new Like(userId, filmId));
    }

    public List<Film> getToplikedFilms(Integer count) {
        Map<Long, Long> filmIdToLikesNumber = new HashMap<>();
        for (Film film : getAll()) {
            Long id = film.getId();
            long likesNumber = likeStorage.getActivitiesById(id).size();
            filmIdToLikesNumber.put(id, likesNumber);
        }
        return filmIdToLikesNumber
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .sorted(Comparator.reverseOrder())
                .limit(count)
                .map(this::getEntity)
                .collect(Collectors.toList());
    }
}
