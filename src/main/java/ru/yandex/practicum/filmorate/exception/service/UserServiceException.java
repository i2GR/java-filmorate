package ru.yandex.practicum.filmorate.exception.service;

import lombok.Getter;
import ru.yandex.practicum.filmorate.model.entity.User;

/**
 * исключение при обработке имени/логина пользователя
 * поле юзер возвращается как response на HTTP-запрос для прохождения Postman-тестов согласно ТЗ-10
 */
public class UserServiceException extends RuntimeException {

    @Getter
    private final User user;

    /**
     * исключение класса UserService при ошибке
     * @param message переданная информация об причине исключения
     */
    public UserServiceException (String message, User user) {
        super(message);
        this.user = user;
    }
}
