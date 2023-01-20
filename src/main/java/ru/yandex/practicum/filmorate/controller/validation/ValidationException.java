package ru.yandex.practicum.filmorate.controller.validation;

public class ValidationException extends Exception{
    public ValidationException(String message) {
        super(message);
    }
}
