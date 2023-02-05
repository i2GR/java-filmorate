package ru.yandex.practicum.filmorate.service;

import lombok.NonNull;
import ru.yandex.practicum.filmorate.model.entity.Entity;

import javax.validation.Valid;
import java.util.List;

/**
 * интерфейс для сервис-слоя базового CRUD-функционала для объектов с идентификаторами
 * ТЗ-10
 */
public interface EntityServable<T extends Entity> {

    /**
     * добавление экземпляра объекта в приложение
     * @param entity экземпляр объекта
     * @return добавленный экземпляр объекта
     */
    T addNewEntity(@Valid T entity);

    /**
     * получение экземпляра объекта из приложения по идентификатору
     * @param entityId идентификатор
     * @return экземпляр объекта под данным идентификатором
     */
    T getEntity(@NonNull Long entityId);

    /**
     * обновление экземпляра объекта в приложение
     * @param entity экземпляр объекта
     * @return добавленный экземпляр объекта
     */
    T updateEntity(@Valid T entity);

    /**
     * удаление экземпляра объекта из приложения по идентификатору
     * @param entityId идентификатор
     * @return экземпляр объекта, который хранился под данным идентификатором
     */
    T deleteEntity(@NonNull Long entityId);

    /**
     * получение списка всех объектов в приложении
     * @return список экземпляров объектов
     */
    // вопрос к следующим ТЗ - не нужно ли получать Map<>?
    List<T> getAll();
}
