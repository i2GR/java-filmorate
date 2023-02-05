package ru.yandex.practicum.filmorate.exception.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.yandex.practicum.filmorate.exception.ErrorResponse;

/**
 * обработчик исключения хранилища
 * @see StorageDuplicateException
 */
@Slf4j
@ControllerAdvice
public class StorageExceptionHandler {

    /**
     * обработка исключения с отправкой HTTP-кода 404 и объекта вызвавшего исключение
     * реализация для прохождения Postman-тестов
     * @param exception исключение
     */
    @ExceptionHandler(StorageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(StorageNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ErrorResponse("Not found", exception.getMessage());
    }

    /**
     * обработка исключения с отправкой HTTP-кода 400 и объекта вызвавшего исключение
     * реализация для прохождения Postman-тестов
     * @param exception исключение
     */
    @ExceptionHandler(StorageDuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDuplicate(StorageNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ErrorResponse("duplicated", exception.getMessage());
    }
}