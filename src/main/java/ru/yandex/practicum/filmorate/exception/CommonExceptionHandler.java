package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * метод перенаправления исключения валидации параметров передаваемых в ендпойнты
     * @param exception ValidationException
     * в текущей реализации в исключение передается стандартное сообщение исключения
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid (MethodArgumentNotValidException exception) {
        log.warn("Invalid request body111");
        return new ResponseEntity<>(new ErrorResponse("error","Invalid request body")
                , HttpStatus.BAD_REQUEST);
    }

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

    /**
     * обработка исключения с отправкой HTTP-кода 404 и объекта вызвавшего исключение
     * реализация для прохождения Postman-тестов
     *
     * @param exception исключение
     */
    @ExceptionHandler(StorageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFound(StorageNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ErrorResponse("Not found", exception.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNoElement(NoSuchElementException exception) {
        log.warn(exception.getMessage());
        return new ErrorResponse("Not found", "no record DB matched request");
    }

    /**
     * обработка исключения с отправкой HTTP-кода 400 и объекта вызвавшего исключение
     * реализация для прохождения Postman-тестов
     * @param exception исключение
     */
    @ExceptionHandler(StorageDuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleDuplicate(StorageNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ErrorResponse("Duplicate", exception.getMessage());
    }

    /**
     * обработка исключения с отправкой HTTP-кода 400 и объекта вызвавшего исключение
     * реализация для прохождения Postman-тестов
     * @param exception исключение
     */
    @ExceptionHandler(UserServiceException.class)
    @ResponseBody
    public @NotNull ResponseEntity<String> handle(UserServiceException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>(exception.getUser().toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse  handleConstraintViolationException(ConstraintViolationException e) {
        log.info("not valid path request to validation error");
        return new ErrorResponse("not valid path request to validation error", e.getMessage());
    }
}