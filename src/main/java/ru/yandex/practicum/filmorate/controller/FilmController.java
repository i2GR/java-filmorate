package ru.yandex.practicum.filmorate.controller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.controller.validation.FilmValidator;
import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private int id = 0;
    @Setter
    private boolean customValidation = false;
    private final Map<Integer, Film> idMapFilm = new HashMap<>();

    @PostMapping("")
    public Film addNewMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("requested movie add");
        // здесь валидатор остался только для написанных тестов
        if (customValidation) film = FilmValidator.validate(film);
        if (film.getId() == 0) {
            film.setId(++id);
        }
        if (!idMapFilm.containsKey(film.getId())) {
            idMapFilm.put(film.getId(), film);
            log.info("movie added");
            return film;
        }
        throw new ValidationException("movie to add already exists");
    }

    @PutMapping("")
    public Film updateMovie(@Valid @RequestBody Film film) throws ValidationException {
        log.info("requested movie update ");
        // здесь валидатор остался только для написанных тестов
        if (customValidation) film = FilmValidator.validate(film);
        if (idMapFilm.containsKey(film.getId())) {
            idMapFilm.remove(film.getId());
            idMapFilm.put(film.getId(), film);
            log.info("movie updated");
            return film;
        }
        throw new ValidationException("film is not registered");
    }

    @GetMapping("")
    public List<Film> getAllMovies(){
        log.info("requested films list");
        return new ArrayList<>(idMapFilm.values());
    }
}