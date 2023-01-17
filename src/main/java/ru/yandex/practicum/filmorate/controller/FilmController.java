package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.controller.validation.FilmValidator;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/movie")
public class FilmController {

    private final Map<Integer, Film> idMapFilm = new HashMap<>();

    @PostMapping("/new")
    public Film addNewMovie(@RequestBody Film film) throws ValidationException {
        log.info("requested movie add");
        film = FilmValidator.validate(film);
        if (!idMapFilm.containsValue(film)) {
            idMapFilm.put(film.getId(), film);
            log.info("movie added");
            return film;
        }
        log.info("movie not added. duplicated");
        return film;
    }

    @PutMapping("/update")
    public Film updateMovie(@RequestBody Film film) throws ValidationException {
        log.info("requested movie update ");
        film = FilmValidator.validate(film);
        if (idMapFilm.containsValue(film)) {
            idMapFilm.put(film.getId(), film);
            log.info("movie updated");
            return film;
        }
        log.info("movie not updated. no movie found");
        return film;
    }

    @GetMapping("/list")
    public List<Film> getAllMovies(){
        log.info("requested films list");
        return new ArrayList<>(idMapFilm.values());
    }
}