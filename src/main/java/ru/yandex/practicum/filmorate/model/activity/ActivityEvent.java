package ru.yandex.practicum.filmorate.model.activity;

import java.util.Optional;

/**
 * абстрактный класс-маркер для событий: лайков и изменения статусов друзей
 * предусматривается, что экземпляр объекта события связывает по идентификаторам пару объектов-моделей
 * (пользователь-пользователь) или (пользователь-фильм)
 * ТЗ-10
 */
public abstract class ActivityEvent {

    /**
     * получение по идентификатору одной "связанной" сущности идентификатора другой связанной событием сущности
     * например по идентификатору пользователя -> идентификатор фильма.
     * если другое поле экземпляра ActivityEvent содержит идентификатор запрошенной сущности
     *
     * @param id идентификатор сущности, для которого проводится "поиск" id друга
     * @return Long со значением идентификатора другой сущности
     */
    public abstract Optional<Long> getPairedId(Long id);
}
