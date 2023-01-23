package ru.yandex.practicum.filmorate.controller.validation;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * обработчик исключений
 */
@Slf4j
@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * обработчик исключения согласно ТЗ
      * @param exception исключение согласно ТЗ
     * @param request Webrequest
     * @return ResponseEntity
     * @throws Exception исключение
     */
    @ExceptionHandler(ValidationException.class)
        public ResponseEntity<Object> handleValidationException(ValidationException exception, WebRequest request)
                                      throws Exception {
        log.warn("Invalid request body: {}", exception.getMessage());
        return super.handleException(exception, request);
    }

    /**
     * обработка исключений валидации с помощью аннотаций
     * с целью отправки в теле ответа тела запроса (для прохождения Postman тестов
     * @param exception исключение ,выбрасываемое по умолчанию Spring-boot
     * @param headers HttpHeaders параметры стандартного метода
     * @param status HttpStatus
     * @param request WebRequest
     * @return исключение согласно ТЗ
     */
    @Override
    public @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException exception,
                                                                        @NotNull HttpHeaders headers,
                                                                        @NotNull HttpStatus status,
                                                                        @NotNull WebRequest request) {
        try {
            return handleValidationException(new ValidationException(exception.getMessage()), request);
        } catch (Exception e) {
            return new ResponseEntity<>(request, HttpStatus.BAD_REQUEST);
        }
    }
}