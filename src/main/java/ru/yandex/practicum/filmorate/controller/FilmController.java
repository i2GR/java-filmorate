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
@RequestMapping("/films")
public class FilmController {
    private int id = 0;
    private final Map<Integer, Film> idMapFilm = new HashMap<>();

    @PostMapping("")
    public Film addNewMovie(@RequestBody Film film) throws ValidationException {
        log.info("requested movie add");
        film = FilmValidator.validate(film);
        if (film.getId() == 0) {
            film.setId(++id);
        }
        if (!idMapFilm.containsValue(film)) {
            idMapFilm.put(film.getId(), film);
            log.info("movie added");
            return film;
        }
        log.info("movie not added. duplicated");
        throw new ValidationException("movie not added. duplicated");
    }

    @PutMapping("")
    public Film updateMovie(@RequestBody Film film) throws ValidationException {
        log.info("requested movie update ");
        film = FilmValidator.validate(film);
        if (idMapFilm.containsKey(film.getId())) {
            idMapFilm.remove(film.getId());
            idMapFilm.put(film.getId(), film);
            log.info("movie updated");
            return film;
        }
        log.info("movie not updated. no movie found");
        throw new ValidationException("movie not updated. no movie found");
    }

    @GetMapping("")
    public List<Film> getAllMovies(){
        log.info("requested films list");
        return new ArrayList<>(idMapFilm.values());
    }
}