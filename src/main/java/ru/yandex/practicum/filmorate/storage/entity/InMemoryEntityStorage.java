package ru.yandex.practicum.filmorate.storage.entity;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.exception.StorageDuplicateException;
import ru.yandex.practicum.filmorate.exception.StorageNotFoundException;
import ru.yandex.practicum.filmorate.model.entity.Entity;

import java.util.*;


/**
 * ТЗ-10 <p>
 * Хранилище в ОЗУ с базовым функционалом для объектов с идентификаторами (пользователи, фильмы)
 * абстрактный класс для избежания повторяющегося кода
 * @param <T> подкласс Entity, для которого определен метод получения по идентификатору
 */
@Slf4j
@RequiredArgsConstructor
public abstract class InMemoryEntityStorage<T extends Entity> implements EntityStorable<T> {

    @NonNull
    private String entityType;

    private final Map<Long, T> inMemoryData = new HashMap<>();

    protected static Logger log() {
        return log;
    }

    @Override
    public Optional<T> create(T entity) {
        log.debug("storing {}", entityType);
        long id = entity.getId();
        if (inMemoryData.putIfAbsent(id, entity) == null) { // ключа нет в мапе -> добавление KV-пары
            log.debug("{} stored in memory under id {}", entityType, id);
            return Optional.of(entity);
        }
        throw new StorageDuplicateException(String.format("%s to add already exists", entityType));
    }

    @Override
    public Optional<T> readById(Long entityId) {
        log.debug("reading {}", entityType);
        T entity = inMemoryData.get(entityId);
        if (entity == null) {
            throw new StorageNotFoundException(String.format("%s with id %s not exists", entityType, entityId.toString()));
        }
        log.debug("{} read in memory with id {}", entityType, entityId);
        return Optional.of(entity);
    }

    @Override
    public Optional<T> update(T entity) {
        log.debug("modifying {}", entityType);
        if (inMemoryData.replace(entity.getId(), entity) == null) { // ключа нет в мапе -> нельзя обновить
            throw new StorageNotFoundException(String.format("%s is not present in memory filmStorage", entityType));
        }
        log.debug(entityType + " updated");
        return Optional.of(entity);
    }

    @Override
    public boolean delete(Long entityId) {
        log.debug("deleting {} with id {} ", entityType, entityId);
        T entity = inMemoryData.remove(entityId);
        if (entity == null) {
            throw new StorageNotFoundException(String.format("%s is not present in memoryFilmStorage", entityType));
        }
        log.debug("deleted {} with ID {}", entityType, entity.getId());
        return true;
    }

    @Override
    public List<T> readAll() {
        log.debug("requested {}s list", entityType);
        return new ArrayList<>(inMemoryData.values());
    }
}