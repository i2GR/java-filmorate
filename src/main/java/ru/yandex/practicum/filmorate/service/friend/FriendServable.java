package ru.yandex.practicum.filmorate.service.friend;

import ru.yandex.practicum.filmorate.model.activity.FriendPair;

/**
 * интерфейс для сервис-слоя базового CRUD-функционала для статуса друзей
 * ТЗ-10
 */
public interface FriendServable {

    /**
     * получение статуса друзей двух пользователей
     * @param ownerId идентификатор пользователя 1, добавляющего другого пользователя
     * @param friendId идентификатор пользователя, добавляемого в друзья
     * @return DTO-класс с полями - идентификаторами пользователей
     * @implNote DTO класс должен предусматривать, что userId1 < userId2
     * <p><p> ТЗ-11:<p>пользователь с receiverId получает пользователя с initiatorId в список друзей
     * при этом у пользователя с initiatorId в списке друзей не будет пользователя с receiverId
     */
    FriendPair joinUpFriends(Long ownerId, Long friendId);

    /**
     * прекращение статуса друзей двух пользователей
     * @param ownerId идентификатор пользователя, который удаляет другого пользователя
     * @param friendId идентификатор пользователя, удаляемого из друзей
     * @return DTO-класс с полями -  идентификаторами пользователей
     */
    FriendPair breakFriends(Long ownerId, Long friendId);
}