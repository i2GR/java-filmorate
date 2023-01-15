package ru.yandex.practicum.filmorate.controller.validation;

import java.util.function.Predicate;

public class ValidationException extends Exception{
    public ValidationException(String message) {
        super(message);
    }
}
