package ru.yandex.practicum.filmorate.storage.activity.friends;

import ru.yandex.practicum.filmorate.model.activity.FriendPair;

import java.util.Optional;
import java.util.Set;

/**
 * ТЗ-10<p>
 * Интерфейс для хранилища статусов друзей пользователей
 */
public interface FriendsStorable {

    /**
     * сохранение статуса друзей
     * @param ownerId идентификатор добавляющего пользователя
     * @param friendId идентификатор пользователя-"друга
     * @return сохраненный экземпляр
     */
    Optional<FriendPair> create(Long ownerId, Long friendId);

    /**
     * проверка статуса друзей двух пользователей в хранилище
     * @param friendPair искомый статус
     * @return true, если статус найдено (пользователи являются друзьями), и false если события нет
     */
    //boolean read(FriendPair friendPair);

    /**
     * удаление статуса друзей
     * @implNote метод подразумевает что статус должен быть удален по любому совпадению идентификаторов пользователя
     * @param ownerId идентификатор пользователя, для которого удаляется "друг"
     * @param friendId идентификатор пользователя-"друга" для удаления
     * @return экземпляр, удаленный и полученный из хранилища
     */
    Optional<FriendPair> delete(Long ownerId, Long friendId);

}