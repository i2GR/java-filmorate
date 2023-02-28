package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.BasicModelHandling;
import ru.yandex.practicum.filmorate.exception.UserServiceException;
import ru.yandex.practicum.filmorate.model.entity.User;

/**
 * интерфейс-маркер для сервис-слоя базового CRUD-функционала для пользователей
 * ТЗ-10
 */
public interface UserServable extends BasicModelHandling<User> {


    /**
     * метод для подстановки логина пользователя в качестве имени при незаполненном поле Name
     * @param user экз. User из запроса
     * @return экз. User с заполенным полем Name
     * @throws UserServiceException в случае любой ошибки в экземпляре User
     */
    default User renameOnLogin(User user) throws UserServiceException {
        try {
            String userName = user.getName();
            if (userName == null || userName.isBlank()) {
                user.setName(user.getLogin());
                //log.info("User.name received is empty. User renamed based on Login");
            }
        } catch (RuntimeException t) {
            throw new UserServiceException("Non correct User object received:incorrect User login", user);
        }
        return user;
    }
}