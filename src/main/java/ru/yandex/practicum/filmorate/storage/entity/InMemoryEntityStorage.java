package ru.yandex.practicum.filmorate.storage.entity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.entity.Entity;
import ru.yandex.practicum.filmorate.utils.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class InMemoryEntityStorage<T extends Entity>{
    private long id = 0;

    @Getter
    private String entityType;

    private Map<Long, T> inMemoryData;

    public InMemoryEntityStorage(EntityType type) {
        this.entityType = type.val();
        inMemoryData = new HashMap<>();
    }

    protected static Logger log() {
        return log;
    }

    protected long checkId(T entity) {
        Long idOfEntity = entity.getId();
        if (idOfEntity == null) {
            entity.setId(++id);
            log.debug("assigned new ID {} to {}", id, entityType);
            return id;
        }
        log.debug("ID {} received by Entity {} used", id, entityType);
        return idOfEntity;
    }

    //@Override
    public T create(T entity) {
        log.debug("storing {}", entityType);
        long id = checkId(entity);
        if (inMemoryData.putIfAbsent(id, entity) == null) { // ключа нет в мапе -> добавление KV-пары
            log.debug("{} stored in memory under id {}", entityType, id);
            return entity;
        }
        throw new ValidationException(String.format("%s to add already exists", entityType));
    }

    public T read(Long entityId) {
        log.debug("reading {}", entityType);
        T entity = inMemoryData.get(entityId);
        if (entity == null) {
            throw new ValidationException(String.format("%s to add already exists", entityType));
        }
        log.debug("{} read in memory with id {}", entityType, entityId);
        return entity;
    }

    //@Override
    public T update(T entity) {
        log.debug("modifying {}", entityType);
        if (inMemoryData.replace(entity.getId(), entity) == null) { // ключа нет в мапе -> нельзя обновить
            throw new ValidationException(String.format("%s is not present in memory storage", entityType));
        }
        log.debug(entityType + " updated");
        return entity;
    }

    //@Override
    public T delete(Long entityId) {
        log.debug("deleting {} with id {} ", entityType, entityId);
        T entity = inMemoryData.remove(id);
        if (entity == null) {
            throw new ValidationException(String.format("%s is not present in memory storage", entityType));
        }
        log.debug("deleted {} with ID {}", entityType, entity.getId());
        return entity;
    }

    //@Override
    public List<T> getAll() {
        log.debug("requested {}s list", entityType);
        return new ArrayList<>(inMemoryData.values());
    }
}
