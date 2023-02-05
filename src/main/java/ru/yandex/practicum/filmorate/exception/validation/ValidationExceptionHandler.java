package ru.yandex.practicum.filmorate.exception.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;

/**
 * обработчик исключений валидации
 * ТЗ-9
 */
@Slf4j
@RestControllerAdvice("ru.yandex.practicum.filmorate.controller.UserController")
public class ValidationExceptionHandler {
    /**
     * обработчик исключения согласно ТЗ
     * @param exception исключение согласно ТЗ
     * @return ResponseEntity
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleValidationException(ValidationException exception) {
        log.warn("Invalid request body: {}", exception.getMessage());
        return new ErrorResponse("Invalid request body", exception.getMessage());
    }
}