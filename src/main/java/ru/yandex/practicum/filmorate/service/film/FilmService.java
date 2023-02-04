package ru.yandex.practicum.filmorate.service.film;

import org.springframework.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.BasicEntityService;
import ru.yandex.practicum.filmorate.service.like.LikeServable;
import ru.yandex.practicum.filmorate.storage.activity.likes.LikeStorable;
import ru.yandex.practicum.filmorate.storage.entity.film.FilmStorage;
import ru.yandex.practicum.filmorate.utils.Constants;
import ru.yandex.practicum.filmorate.utils.EntityType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
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

    public List<Film> getToplikedFilms(@NonNull Integer count) {
        Map<Long, Film> filmToLikesNumber = new HashMap<>();
        for (Film film : getAll()) {
            Long likesNumber = (long) likeStorage.getActivitiesById(film.getId()).size();
            filmToLikesNumber.put(likesNumber, film);
        }
        return filmToLikesNumber
                .keySet()
                .stream()
                .sorted()
                .limit((count == null) ? Constants.DEFAULT_POPULAR_FILMS_AMOUNT : count)
                .map(id -> filmToLikesNumber.get(id))
                .collect(Collectors.toList());
    }
}
