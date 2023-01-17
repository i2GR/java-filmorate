package ru.yandex.practicum.filmorate.controller.validation;

public class StringValidator {

   public static boolean checkEmpty(String param, String field) throws ValidationException {
        if (param == null || param.isBlank()) throw new ValidationException("Field is Empty:" + field);
        return true;
   }

    public static boolean checkLength(int param, int limit, String field) throws ValidationException {
        if (limit < param) throw new ValidationException(String.format("Field %s exceeds specified limit %d", field, limit));
        return true;
   }
}
