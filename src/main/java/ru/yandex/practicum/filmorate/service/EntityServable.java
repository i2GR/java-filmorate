package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.entity.Entity;

import java.util.List;

public interface EntityServable<T extends Entity> {

    public T addNewEntity(T entity);

    public T getEntity(Long entityId);

    public T updateEntity(T entity);

    public T deleteEntity(Long entityId);

    public List<T> getAll();
}
