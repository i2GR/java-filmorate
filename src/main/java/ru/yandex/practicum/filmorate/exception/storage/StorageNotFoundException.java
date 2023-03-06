package ru.yandex.practicum.filmorate.exception.storage;

/**
 * исключение при операциях в слое хранилища при отсутствии сущности в хранилище
 * ТЗ-10
 */
public class StorageNotFoundException extends RuntimeException{
    public StorageNotFoundException(String message) {
        super(message);
    }
}