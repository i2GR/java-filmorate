package ru.yandex.practicum.filmorate.service.friend;

import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;

import java.util.List;

/**
 * интерфейс для сервис-слоя базового CRUD-функционала для статуса друзей
 * ТЗ-10
 */
public interface FriendServable {

    /**
     * получение статуса друзей двух пользователей
     * @param userId1 идентификатор пользователя 1 (условный)
     * @param userId2 идентификатор пользователя 2 (условный)
     * @return DTO-класс с полями - идентификаторами пользователей
     * @implNote DTO класс должен предусматривать, чтобы поля были симметричны
     * два объекта должны быть равны,
     * если userId1(1) = userId1(2) || userId2(1) = userId2(2) || userId1(2) = userId2(1) || userId2(2) = userId1(1)
     */
    FriendPair joinUpFriends(Long userId1, Long userId2);

    /**
     * проверка являются ли два пользователя "друзьями"
     * @param userId1 идентификатор пользователя 1 (условный)
     * @param userId2 идентификатор пользователя 2 (условный)
     * @return true, если являются, false - иначе
     */
    boolean areFriends(Long userId1, Long userId2);

    /**
     * прекращение статуса друзей двух пользователей
     * @param userId1 идентификатор пользователя 1 (условный)
     * @param userId2 идентификатор пользователя 2 (условный)
     * @return DTO-класс с полями -  идентификаторами пользователей
     */
    FriendPair breakFriends(Long userId1, Long userId2);

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
