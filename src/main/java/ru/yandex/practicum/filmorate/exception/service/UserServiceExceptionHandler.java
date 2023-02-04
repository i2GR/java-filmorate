package ru.yandex.practicum.filmorate.exception.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.model.entity.User;


/**
 * обработчик исключения
 * @see UserServiceException
 *
 */
@Slf4j
@ControllerAdvice
public class UserServiceExceptionHandler {
    /**
     * обработка исключения с отправкой HTTP-кода 400 и объекта вызвавшего исключение
     * реализация для прохождения Postman-тестов
     * @param exception исключение
     */
    @ExceptionHandler(UserServiceException.class)
    public @NotNull ResponseEntity<String> handle(final UserServiceException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getUser().toString(), HttpStatus.BAD_REQUEST);
    }
}