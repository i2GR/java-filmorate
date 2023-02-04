package ru.yandex.practicum.filmorate.exception.storage;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.yandex.practicum.filmorate.exception.validation.ValidationException;

/**
 * обработчик исключения хранилища
 * @see StorageDuplicateException
 *
 */
@Slf4j
@ControllerAdvice
public class StorageExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * обработка исключения с отправкой HTTP-кода 404 и объекта вызвавшего исключение
     * реализация для прохождения Postman-тестов
     * @param exception исключение
     */
    @ExceptionHandler(StorageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
/*    public @NotNull ResponseEntity<String> handle(final StorageNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }*/
    public ResponseEntity<Object> handleNotFound(StorageNotFoundException exception, WebRequest request)
            throws Exception {
        log.warn(exception.getMessage());
        return super.handleException(exception, request);
    }

    /**
     * обработка исключения с отправкой HTTP-кода 400 и объекта вызвавшего исключение
     * реализация для прохождения Postman-тестов
     * @param exception исключение
     */
    @ExceptionHandler(StorageDuplicateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
/*    public @NotNull ResponseEntity<String> handle(final StorageDuplicateException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getObject().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
    public ResponseEntity<Object> handleDuplicate(StorageDuplicateException exception, WebRequest request)
            throws Exception {
        log.warn(exception.getMessage());
        return super.handleException(exception, request);
    }
}