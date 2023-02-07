package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.BasicModelHandling;
import ru.yandex.practicum.filmorate.ValidThrowable;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.film.FilmServable;

import javax.validation.Valid;
import java.util.List;

/**
 * реализация  базового CRUD-функционала ендпойнтов для фильмов
 * ТЗ-9
 * @see ValidThrowable
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/films")
public class FilmController implements BasicModelHandling<Film>, ValidThrowable {

    @NonNull
    private final FilmServable filmService;

    @Override
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("add film request");
        return filmService.create(film);
    }

    @Override
    @GetMapping("/{id}")
    public Film readById(@Valid @PathVariable Long id) {
        log.info("get film request with id {}", id);
        return filmService.readById(id);
    }

    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("update film request with id {}", film.getId());
        return filmService.update(film);
    }

    @Override
    @GetMapping
    public List<Film> readAll() {
        log.info("get all films request");
        return filmService.readAll();
    }

    @Override
    public Logger log() {
        return log;
    }
}