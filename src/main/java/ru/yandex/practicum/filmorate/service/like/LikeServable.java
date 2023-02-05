package ru.yandex.practicum.filmorate.service.like;

import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.util.List;

/**
 * интерфейс для сервис-слоя базового CRUD-функционала для статуса друзей
 * ТЗ-10
 */
public interface LikeServable {

    /**
     * установка лайка от пользователя на фильм
     * @param userId идентификатор пользователя
     * @param filmId идентификатор фильма ,которому ставится лайк
     * @return DTO-класс лайка
     */
    Like like(Long userId, Long filmId);

    /**
     * удаления лайка на фильм от пользователя
     * @param userId идентификатор пользователя
     * @param filmId идентификатор фильма ,которому ставится лайк
     * @return DTO-класс лайка
     */
    Like dislike(Long userId, Long filmId);

    /**
     * получения списка фильмов с наибольшим количеством лайков
     * @param count количество фильмов, которое должно содержатсья в списке
     * @return сортированный список по убыванию
     */
    List<Film> getToplikedFilms(Integer count);
}
