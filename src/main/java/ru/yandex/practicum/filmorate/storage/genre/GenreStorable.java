package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.util.List;
import java.util.Optional;

/**
 * ТЗ-11<p>
 * Интерфейс для хранилища жанров
 */
public interface GenreStorable {
    /**
     * получение списка всех жанров
     * @return список жанров (экземпляры Genre)
     */
    List<Genre> readAll();

    /**
     * получение жанра по идентификатору
     * @param id идентификатор (Integer)
     * @return Optional (экземпляра Genre)
     */
    Optional<Genre> readGenreById(Integer id);

    /**
     * обновление жанров для фильма
     * @param film фильм, сожержащий список жанров
     * @return количество измененных строк в БД (таблица film_genres)
     */
    int updateGenresForFilm(Film film);

    /**
     * получение списка жанров фильма из базы по идентификатору фильма
     * @param filmId идентификатор фильма
     * @return список жанров
     */
    List<Genre> readByFilmId(Long filmId);
}
