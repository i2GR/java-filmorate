package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.entity.Entity;

/**
 * ТЗ-10 (исправление замечаний) <p>
 * Отдельно выделеннный сервис с функционалом присвоением идентификатора.
 * Т.к. присвоение идентификатора - общая функция для сервисов фильмов и пользователей
 * @implNote <u>"идентифицируемая модель"</u> в описании методов это экземпляр класса-наследника {@link Entity} <p>
 * @param <T> указанный экземпляр класса, имеющий поле и сеттер/геттер идентификатора
 */
@Slf4j
public class IdService <T extends Entity> implements IdServable<T> {

    private long lastId;

    @Override
    public Long getNewId() {
        return ++lastId;
    }

    public IdService(long lastId) {
        this.lastId = lastId;
    }

    @Override
    public T getEntityWithCheckedId(T entity) {
        return this.getEntityWithCheckedId(entity, log);
    }
}