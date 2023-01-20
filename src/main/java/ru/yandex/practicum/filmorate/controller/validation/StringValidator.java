package ru.yandex.practicum.filmorate.controller.validation;

/**
 * общий класс с методами фалидации строк согласно первой части ТЗ
 */
public class StringValidator {

   public static boolean checkEmpty(String param, String field) throws ValidationException {
        if (param == null || param.isBlank()) throw new ValidationException(String.format("%s is null or blank:", field));
        return true;
   }

    public static boolean checkLength(int param, int limit, String field) throws ValidationException {
        if (limit < param) throw new ValidationException(String.format("Field %s exceeds specified limit %d", field, limit));
        return true;
   }

}
