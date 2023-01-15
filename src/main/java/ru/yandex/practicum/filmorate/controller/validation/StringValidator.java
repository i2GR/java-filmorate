package ru.yandex.practicum.filmorate.controller.validation;

import java.time.LocalDate;

public class DataValidator {

    public static void checkEmpty(String param, String field) throws ValidationException {
        if (param.isBlank()) throw new ValidationException("Field is Empty:" + field);
    }

    public static void checkLength(int param, int limit, String field) {
        if (limit < param) throw new ValidationException(String.format("Field %s exceeds specified limit %d", field, limit);
    }
}
