package ru.yandex.practicum.filmorate.storage.activity.friends;

import ru.yandex.practicum.filmorate.model.activity.FriendPair;

import java.util.Set;

/**
 * ТЗ-10<p>
 * Интерфейс для хранилища статусов друзей пользователей
 */
public interface FriendsStorable {

    /**
     * сохранение статуса друзей
     * @param friendPair экземпляр для сохранения статуса друзей
     * @return сохраненный экземпляр
     */
    FriendPair create(FriendPair friendPair);

    /**
     * проверка статуса друзей двух пользователей в хранилище
     * @param friendPair искомый статус
     * @return true, если статус найдено (пользователи являются друзьями), и false если события нет
     */
    boolean read(FriendPair friendPair);

    /**
     * удаление статуса друзей
     * @implNote метод подразумевает что статус должен быть удален по любому совпадению идентификаторов пользователя
     * @param friendPair искомый статус
     * @return экземпляр, удаленный и полученный из хранилища
     */
    FriendPair delete(FriendPair friendPair);

    /**
     * получение списка всех друзей пользователя по переданному идентификатору
     * @param userId идентификатор искомого пользователя
     * @return Сет идентификаторов пользователей-друзей
     */
    Set<Long> getFriendsById(Long userId);
}