package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.EntityServable;

import javax.validation.Valid;
import java.util.List;

/**
 * реализация  базового CRUD-функционала ендпойнтов для фильмов
 * ТЗ-9
 * @see BasicController
 */
@RestController
@RequestMapping("/films")
public class FilmController extends BasicController<Film> {

    public FilmController(EntityServable<Film> service) {
        super(service);
    }

    @Override
    @PostMapping
    public Film addNew(@Valid @RequestBody Film film) {
        log().info("add film request");
        return super.addNew(film);
    }

    @Override
    @GetMapping("/{id}")
    public Film getById(@Valid @PathVariable Long id) {
        log().info("get film request with id {}", id);
        return super.getById(id);
    }

    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log().info("update film request with id {}", film.getId());
        return super.update(film);
    }

    @Override
    @GetMapping
    public List<Film> getAll() {
        log().info("get all films request");
        return super.getAll();
    }
}