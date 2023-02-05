package ru.yandex.practicum.filmorate.exception.validation;

/**
 * исключение при операциях валидации аргументов методов на ендпойнтах
 * ТЗ-9
 */
public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
