package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class BasicController<T extends Entity> {
    private int id;
    private final String dtoName;
    private final Map<Integer, T> idMapDTO = new HashMap<>();

    protected BasicController(@NonNull String dtoName) {
        this.dtoName = dtoName;
    }

    protected static Logger getLog() {
        return log;
    }

    protected T addNew( T item) {
        log.info("requested {} to add", dtoName);
        if (item.getId() == 0) {
            item.setId(++id);
        }
        if (idMapDTO.putIfAbsent(item.getId(), item) == null) { // ключа нет в мапе -> добавление KV-пары
            log.info("{} added", dtoName);
            return item;
        }
        throw new ValidationException(String.format("%s to add already exists", dtoName));
    }

    protected T update(T item) {
        log.info("requested {} update", dtoName);
        if (idMapDTO.replace(item.getId(), item) == null) { // ключа нет в мапе -> нельзя обновить
            throw new ValidationException(String.format("%s is not registered", dtoName));
        }
        log.info(dtoName + " updated");
        return item;
    }

    protected List<T> getAll() {
        log.info("requested {}s list", dtoName);
        return new ArrayList<>(idMapDTO.values());
    }

}
