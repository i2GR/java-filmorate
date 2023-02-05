package ru.yandex.practicum.filmorate.storage.activity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.exception.storage.StorageDuplicateException;
import ru.yandex.practicum.filmorate.exception.storage.StorageNotFoundException;
import ru.yandex.practicum.filmorate.model.activity.ActivityEvent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Хранилище в ОЗУ с базовым функционалом для активностей
 * ТЗ-10
 * @param <T> подкласс Активности, для которого определен метод получения по идентификатору
 */
@Slf4j
@RequiredArgsConstructor
public abstract class InMemoryActivityStorage<T extends ActivityEvent> {

    @Getter
    private final String activityType;

    private final Set<T> inMemoryData = new HashSet<>();

    protected static Logger log() {
        return log;
    }

    //@Override
    public T create(T activity) {
        log.debug("creating {}", activityType);
        if (inMemoryData.add(activity)) { // события не было -> добавление
            log.debug("{} recorded in memory", activityType);
            return activity;
        }
        throw new StorageDuplicateException(String.format("%s to add is already exists", activityType), activity);
    }

    //@Override
    public boolean read(T activity) {
        log.debug("reading {}", activityType);
        return inMemoryData.contains(activity);
    }

    //@Override
    public T delete(T activity) {
        log.debug("deleting {}", activityType);
        if (inMemoryData.remove(activity)) {
            log.debug("deleted {}", activityType);
            return activity;
        }
        throw new StorageNotFoundException(String.format("%s is not present in memory storage", activityType));
    }

    //@Override
    public Set<Long> getActivitiesById(Long id) {
        log.debug("get List of {} by id {}", activityType, id);
        return inMemoryData.stream()
                            .map(activity -> activity.getPairedId(id))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toSet());
    }
}
