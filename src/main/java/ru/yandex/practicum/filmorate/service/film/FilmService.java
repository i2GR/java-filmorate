package ru.yandex.practicum.filmorate.service.film;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.BasicEntityService;
import ru.yandex.practicum.filmorate.storage.activity.likes.LikeStorable;
import ru.yandex.practicum.filmorate.storage.entity.film.FilmStorage;
import ru.yandex.practicum.filmorate.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilmService extends BasicEntityService<Film> implements FilmServable{

    @NonNull
    @Autowired
    private final LikeStorable likeStorage;

    public FilmService(@NonNull LikeStorable likeStorage, @NonNull FilmStorage filmStorage) {
        super(filmStorage);
        this.likeStorage = likeStorage;
    }

    public Like like(Long userId, Long filmId) {
        return likeStorage.create(new Like(userId, filmId));
    }

    public Like dislike(Long userId, Long filmId) {
        return likeStorage.delete(new Like(userId, filmId));
    }

    public List<Film> getTop10likedFilms(Long filmId) {
        Map<Long, Film> filmToLikesNumber = new HashMap<>();
        for (Film film : getAll()) {
            Long likesNumber = (long) likeStorage.getActivitiesById(film.getId()).size();
            filmToLikesNumber.put(likesNumber, film);
        }
        return filmToLikesNumber
                .keySet()
                .stream()
                .sorted()
                .limit(Constants.MOST_POPULAR_FILMS_AMOUNT)
                .map(id -> filmToLikesNumber.get(id))
                .collect(Collectors.toList());
    }
}
