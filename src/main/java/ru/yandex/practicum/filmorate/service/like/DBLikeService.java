package ru.yandex.practicum.filmorate.service.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DBLikeStorage;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.util.List;
import java.util.Optional;

/**
 * реализация CRUD-функционала в сервис слое для лайков
 * хранение данных в БД
 * ТЗ-11
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class DBLikeService implements LikeServable{

    /**
     * хранилище в БД
     */
    @NonNull
    private DBLikeStorage likeDBstorage;

    @Override
    public Like like(Long userId, Long filmId){
        Like like = new Like(userId, filmId);
        Optional<Like> optionalLike = likeDBstorage.create(like);
        log.info("received response from DB {} when record:", optionalLike.isPresent());
        return optionalLike.orElseThrow();
    }

    @Override
    public Like dislike(Long userId, Long filmId){
        Like like = new Like(userId, filmId);
        Optional<Like> optionalLike = likeDBstorage.delete(like);
        log.info("received response from DB {} when delete:", optionalLike.isPresent());
        return optionalLike.orElseThrow();
    }

    @Override
    public List<Film> getTopLikedFilms(Integer count) {
        List<Film> topFilmCount = likeDBstorage.getTopFilms(count);
        log.info("received data from DB: {}", topFilmCount.size());
        return topFilmCount;
    }
}