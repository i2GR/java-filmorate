package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;

/**
 * класс ответа на HTTP-запрос при выбрасывании исключения на Ендпойнте
 * ТЗ-10
 */
public class ErrorResponse {

    @Getter
    String error;
    @Getter
    String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
