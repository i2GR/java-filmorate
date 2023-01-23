package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/films")
public class FilmController extends BasicController<Film> {

    public FilmController() {
        super("Film");
    }

    @PostMapping
    public Film addNewMovie(@Valid @RequestBody Film film) {
        return super.addNew(film);
    }

    @PutMapping
    public Film updateMovie(@Valid @RequestBody Film film) {
        return super.update(film);
    }

    @GetMapping
    public List<Film> getAllMovies() {
        return super.getAll();
    }
}