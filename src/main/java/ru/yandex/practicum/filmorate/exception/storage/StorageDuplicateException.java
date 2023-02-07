package ru.yandex.practicum.filmorate.exception.storage;

/**
 * исключение при операциях в слое хранилища при наличии дупликатов сущностей
 * ТЗ-10
 */
public class StorageDuplicateException extends RuntimeException{

    public StorageDuplicateException(String message) {
        super(message);
    }
}