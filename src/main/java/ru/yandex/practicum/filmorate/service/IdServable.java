package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.entity.Entity;

/**
 * ТЗ-10 (исправление замечаний) <p>
 * Отдельно выделеннный интерфейс функционала присвоением идентификатора.
 * Т.к. присвоение идентификатора - общая функция для сервисов фильмов и пользователей
 * @implNote <u>"идентифицируемая модель"</u> в описании методов это экземпляр класса-наследника {@link Entity} <p>
 * @param <T> указанный экземпляр класса, имеющий поле и сеттер/геттер идентификатора
 */
public interface IdServable<T extends Entity> {

    Long getNewId();

    T getEntityWithCheckedId(T entity);

    default T getEntityWithCheckedId(T entity, org.slf4j.Logger log) {
        Long id = entity.getId();
        if (id == null) {
            id = getNewId();
            entity.setId(id);
            log.debug("assigned new ID {} to {}", id, entity.getClass().getName());
            return entity;
        }
        log.debug("ID {} received by Entity {} used", id, entity.getClass().getName());
        return entity;
    }
}