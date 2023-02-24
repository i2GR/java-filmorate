package ru.yandex.practicum.filmorate.exception.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;

/**
 * обработчик исключений валидации
 * ТЗ-9
 */
@Slf4j
@ControllerAdvice
public class ValidationExceptionHandler {
    /**
     * обработчик исключения согласно ТЗ
     * @param exception исключение согласно ТЗ
     * @return унифицированное сообщение об ошибке
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
    @ResponseBody
    public ErrorResponse handleValidationException(ValidationException exception) {
        log.warn("Invalid request body: {}", exception.getMessage());
        return new ErrorResponse("Invalid request body", exception.getMessage());
    }
}