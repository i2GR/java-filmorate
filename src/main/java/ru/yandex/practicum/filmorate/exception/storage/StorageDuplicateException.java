package ru.yandex.practicum.filmorate.exception.storage;

import lombok.Getter;
import ru.yandex.practicum.filmorate.model.entity.Entity;

/**
 * исключение при операциях в слое хранилища при наличии дупликатов сущностей
 * ТЗ-10
 */
public class StorageDuplicateException extends RuntimeException{
    @Getter
    Object object;

    public StorageDuplicateException(String message, Object o) {
        super(message);
        object = (o instanceof Entity) ? (Entity) o : o;
    }
}
