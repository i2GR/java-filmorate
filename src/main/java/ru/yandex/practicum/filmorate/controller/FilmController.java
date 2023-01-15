package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.controller.validation.FilmValidator;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")
public class FilmController {

    private final Map<Integer, Film> idMapFilm = new HashMap<>();

    @PostMapping("/new")
    public Film addNewMovie(@RequestBody Film film) throws ValidationException {
        film = FilmValidator.validate(film);
        return film;
    }

    @PutMapping("/update")
    public Film updateMovie(@RequestBody Film film){
        //TODO
        return film;
    }

    @GetMapping("/list")
    public List<Film> getAllMovies(){
        //TODO testlog
        return new ArrayList<>(idMapFilm.values());
    }
}
