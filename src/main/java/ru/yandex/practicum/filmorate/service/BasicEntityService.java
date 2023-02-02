package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.entity.Entity;
import ru.yandex.practicum.filmorate.storage.entity.EntityStorable;

import java.util.List;

@RequiredArgsConstructor
public abstract class BasicEntityService<T extends Entity> {

    @Autowired
    protected final EntityStorable<T> storage;

    public T addNewEntity(T entity) {
        return storage.create(entity);
    }

    public T getEntity(Long entityId){
        return storage.read(entityId);
    }

    public T updateEntity(T entity) {
        return storage.update(entity);
    }

    public T deleteEntity(Long entityId) {
        return storage.delete(entityId);
    }

    public List<T> getAll() {
        return storage.getAll();
    }
}
