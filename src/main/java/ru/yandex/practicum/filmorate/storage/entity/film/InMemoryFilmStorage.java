package ru.yandex.practicum.filmorate.storage.entity.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.storage.entity.InMemoryEntityStorage;
import ru.yandex.practicum.filmorate.utils.EntityType;

@Component
public class InMemoryFilmStorage extends InMemoryEntityStorage<Film> implements FilmStorage {

    public InMemoryFilmStorage() {
        super(EntityType.FILM);
    }
}
