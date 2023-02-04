package ru.yandex.practicum.filmorate.exception.validation;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
