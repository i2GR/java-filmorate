package ru.yandex.practicum.filmorate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * класс ответа на HTTP-запрос при выбрасывании исключения на Ендпойнте
 * ТЗ-10
 */
@AllArgsConstructor
public class ErrorResponse {

    @Getter
    private final String error;
    @Getter
    private final String description;
}