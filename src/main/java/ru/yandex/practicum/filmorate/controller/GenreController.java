package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.genre.GenreServable;

import javax.validation.Valid;
import java.util.List;

/**
 * реализация функционала ендпойнтов для жанров
 * ТЗ-11
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
public class GenreController {

    @NonNull
    private final GenreServable genreService;

    /**
     * получение списка всех жанров
     * @return список жанров (экземпляры Genre)
     */
    @GetMapping
    public List<Genre> getAll() {
        log.info("get all genres request");
        return genreService.getAll();
    }

    /**
     * получение жанра по идентификатору
     * @param id идентификатор (Integer)
     * @return жанр (экземпляр Genre)
     */
    @GetMapping("/{id}")
    public Genre getById(@Valid @PathVariable Integer id) {
        log.info("get genre request with id {}", id);
        return genreService.getGenreById(id);
    }
}