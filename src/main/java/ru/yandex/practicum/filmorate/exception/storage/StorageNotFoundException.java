package ru.yandex.practicum.filmorate.exception.storage;

public class StorageNotFoundException extends RuntimeException{
    public StorageNotFoundException(String message) {
        super(message);
    }
}
