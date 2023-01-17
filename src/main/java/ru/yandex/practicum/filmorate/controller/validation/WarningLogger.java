package ru.yandex.practicum.filmorate.controller.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WarningLogger {
    @ExceptionHandler(value = ValidationException.class)
    public void logException(ValidationException exception) {
        log.warn(exception.getMessage());
    }
}
