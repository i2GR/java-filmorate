package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam ;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.like.LikeServable;
import ru.yandex.practicum.filmorate.utils.Constants;

import java.util.List;
import java.util.Optional;

/**
 * реализация  базового CRUD-функционала ендпойнтов для лайков
 * ТЗ-10
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class LikeController {

    @NonNull
    private final LikeServable service;

    @PutMapping("/{id}/like/{userId}")
    public Like setLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("user {} liked-up film {}", userId, id);
        return service.like(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Like removeLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("user {} disliked film {}", userId, id);
        return service.dislike(userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getLikedFilms(@RequestParam (name = "count") Optional<Integer> countParam) {
        log.info("requested top films");
        Integer count = countParam.orElse(Constants.DEFAULT_POPULAR_FILMS_AMOUNT);
        return service.getTopLikedFilms(count);
    }
}