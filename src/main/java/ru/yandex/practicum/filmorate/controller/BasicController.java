package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.entity.Entity;
import ru.yandex.practicum.filmorate.service.EntityServable;

import java.util.List;

@RequiredArgsConstructor
public abstract class BasicController<T extends Entity> {

    @Autowired
    @NonNull
    protected final EntityServable<T> service;

    public T addNew(T entity) {
        return service.addNewEntity(entity);
    }

    /**
     * получение объекта Entity по id
     * @param id идентификатор объекта Entity
     * @return экземпляр класса
     */
    public T getById(Long id) {
        return service.getEntity(id);
    }

    public T update(T entity) {
        return service.updateEntity(entity);
    }

    public List<T> getAll() {
        return service.getAll();
    }
}