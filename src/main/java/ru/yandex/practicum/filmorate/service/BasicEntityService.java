package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.entity.Entity;
import ru.yandex.practicum.filmorate.storage.entity.EntityStorable;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class BasicEntityService<T extends Entity> {

    private Long id = 1L;

    @lombok.NonNull
    private final String entityType;

    @Autowired
    protected final EntityStorable<T> storage;

    protected Long setIdByService() {
        return id++;
    }

    public T addNewEntity(T entity) {
        entity = getEntityWithCheckedId(entity);
        return storage.create(entity);
    }

    public T getEntity(@Valid Long entityId){
        return storage.read(entityId);
    }

    public T updateEntity(T entity) {
        return storage.update(entity);
    }

    public T deleteEntity(@NonNull Long entityId) {
        return storage.delete(entityId);
    }

    public List<T> getAll() {
        return storage.getAll();
    }

    protected T getEntityWithCheckedId(T entity) {
        if (entity.getId() == null) {
            entity.setId(setIdByService());
            log.debug("assigned new ID {} to {}", id, entityType);
            return entity;
        }
        log.debug("ID {} received by Entity {} used", id, entityType);
        return entity;
    }
}
