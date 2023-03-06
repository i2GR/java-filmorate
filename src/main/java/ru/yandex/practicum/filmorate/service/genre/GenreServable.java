package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.util.List;
import java.util.Map;

/**
 * интерфейс для сервис-слоя функционала жанров
 * ТЗ-11
 */
public interface GenreServable {

    /**
     * Получение списка жанров
     * предполагается использование для слоя REST-контроллера
     * @return список жанров
     */
    List<Genre> getAll();

    /**
     * Получение жанра по идентификатору
     * предполагается использование для слоя REST-контроллера
     * @param id присвоенный идентификатор жанра (согласно схеме БД ТЗ-11)
     * @return экземпляр жанра, полученный по идентификатору (из БД ТЗ-11)
     */
    Genre getGenreById(Integer id);

    /**
     * Обновление жанров для выбранного объекта фильма
     * @param film объект фильма
     * @return число "записей" жанров присвовенных фильму
     * @implNote НЕ ИСПОЛЬЗУЕТСЯ
     */
    int updateGenreForFilm(Film film);

    /**
     * получение жанров для фильма по идентификатору
     * предполагается реализация получения из хранилища (БД)
     * @param filmId идентификатор фильма
     * @return список жанров фильма
     */
    List<Genre> getGenresForFilmById(Long filmId);

    /**
     * получение таблицы с данными о всех жанрах фильмов
     * реализация в виде Map
     * @return реализация в виде Map
     * ключ - идентификатор фильма
     * значение по ключу - списко жанров, указанных в БД для фильма с заданым идентификатором
     */
    Map<Long, List<Genre>> getFilmGenresCommon();
}