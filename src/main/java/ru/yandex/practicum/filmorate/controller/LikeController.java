package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.like.LikeServable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class LikeController {

    @Autowired
    @NonNull
    private final LikeServable service;

    @PutMapping("/{id}/like/{userId}")
    public Like setLike(@PathVariable Long id, @PathVariable Long userId) {
        return service.like(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Like removeLike(@PathVariable Long id, @PathVariable Long userId) {
        return service.dislike(userId, id);
    }

    @GetMapping("/popular?count={count})")
    public List<Film> getLikedFilms(@PathVariable Integer count) {
        return service.getToplikedFilms(count);
    }
}
