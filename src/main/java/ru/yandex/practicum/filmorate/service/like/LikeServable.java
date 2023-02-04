package ru.yandex.practicum.filmorate.service.like;

import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.util.List;

public interface LikeServable {

    Like like(Long userId, Long filmId);

    Like dislike(Long userId, Long filmId);

    List<Film> getToplikedFilms(Integer count);
}
