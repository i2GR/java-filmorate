package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.model.FilmMpaRating;

import java.util.List;

/**
 * интерфейс для сервис-слоя функционала рейтингов MPA
 * ТЗ-11
 */
public interface MpaRateServable {

    /**
     * Получение списка рейтингов MPA
     * предполагается использование для слоя REST-контроллера
     * @return список рейтингов MPA
     */
    List<FilmMpaRating> getAll();

    /**
     * Получение рейтинга MPA по идентификатору
     * предполагается использование для слоя REST-контроллера
     * @param id присвоенный идентификатор рейтинга MPA (согласно схеме БД ТЗ-11)
     * @return экземпляр рейтинга MPA, полученный по идентификатору (из БД ТЗ-11)
     */
    FilmMpaRating getMpaRateById(Integer id);
}
