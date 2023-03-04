package ru.yandex.practicum.filmorate.service.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * реализация CRUD-функционала в сервис слое для жанров
 * ТЗ-11
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class DBGenreService implements GenreServable {

    /**
     * хранилище в БД
     */
    @NonNull
    private final GenreStorable genreDBStorage;

    @Override
    public List<Genre> getAll() {
        List<Genre> filmList = genreDBStorage.readAll();
        log.info("received data from DB {}", filmList.size());
        return filmList;
    }

    @Override
    public Genre getGenreById(@NonNull Integer id){
        Optional<Genre> optionalGenre = genreDBStorage.readGenreById(id);
        log.info("received data from DB {}", optionalGenre.isPresent());
        return optionalGenre.orElseThrow();
    }

    @Override
    public int updateGenreForFilm(Film film) {
        return genreDBStorage.updateGenresForFilm(film);
    }

    @Override
    public List<Genre> getGenresForFilmById(@NonNull Long filmId) {
        return genreDBStorage.readByFilmId(filmId);
    }

    public Map<Long, List<Genre>> getFilmGenresCommon() {
        return genreDBStorage.getFilmGenresCommon();
    }
}