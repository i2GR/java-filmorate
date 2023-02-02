package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.service.EntityServable;

import javax.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/films")
public class FilmController extends BasicController<Film> {

    public FilmController(EntityServable<Film> service) {
        super(service);
    }

    @Override
    @PostMapping
    public Film addNew(@Valid @RequestBody Film film) {
        return super.addNew(film);
    }

    /**
     * получение фильма по id
     * @implNote ендпойт не указан в ТЗ10
     * @param id идентификатор фильма (запрос)
     * @return экземпляр класса Film
     */
    @Override
    @GetMapping("/{id}")
    public Film getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return super.update(film);
    }

    @Override
    @GetMapping
    public List<Film> getAll() {
        return super.getAll();
    }
}