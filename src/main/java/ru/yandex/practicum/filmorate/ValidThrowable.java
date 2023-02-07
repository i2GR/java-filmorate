package ru.yandex.practicum.filmorate;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;

/**
 * интерфейс с default-обработкой исключения валидации параметров
 * перехватывает Spring-boot исключения handleMethodArgumentNotValid в выброс исключения приложения ValidationException
 * ТЗ-10
 */
public interface ValidThrowable {

    Logger log();

      /**
     * метод перенаправления исключения валидации параметров передаваемых в ендпойнты
     * @param exception ValidationException
     * в текущей реализации в исключение передается стандартное сообщение исключения
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    default ResponseEntity<ErrorResponse> handleMethodArgumentNotValid (MethodArgumentNotValidException exception) {
        log().warn("Invalid request body");
        return new ResponseEntity<>(new ErrorResponse("error","Invalid request body")
                                    , HttpStatus.BAD_REQUEST);
    }
}