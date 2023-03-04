package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.BasicModelHandling;
import ru.yandex.practicum.filmorate.exception.UserServiceException;
import ru.yandex.practicum.filmorate.model.entity.User;

import java.util.List;

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
            }
        } catch (RuntimeException t) {
            throw new UserServiceException("Non correct User object received:incorrect User login", user);
        }
        return user;
    }

    /**
     * получение списка общих друзей для двух пользователей
     * @param userId1 идентификатор пользователя 1 (условный)
     * @param userId2 идентификатор пользователя 2 (условный)
     * @return список
     */
    List<User> getMutualFriends(Long userId1, Long userId2);

    /**
     * получение списка друзей для заданного пользователей
     * @param id идентификатор заданного пользователя
     * @return список друзей
     */
    List<User> getAllFriends(Long id);
}