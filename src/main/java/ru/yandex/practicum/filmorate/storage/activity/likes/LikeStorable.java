package ru.yandex.practicum.filmorate.storage.activity.likes;

import ru.yandex.practicum.filmorate.model.activity.Like;

import java.util.Set;

/**
 * ТЗ-10<p>
 * Интерфейс для хранилища лайков фильмов от пользователей
 */
public interface LikeStorable {

    /**
     * сохранение
     * @param like экземпляр для сохранения лайка
     * @return сохраненный экземпляр
     */
    Like create(Like like);

    /**
     * проверка наличия лайка в хранилище
     * @param like искомый статус
     * @return true, если лайк найден (пользователи являются друзьями), и false если лайка нет
     */
    boolean read(Like like);

    /**
     * удаление лайка
     * @implNote метод подразумевает что лайк должен быть удален совпадению id пользователя и id фильма
     * @param like искомый лайк
     * @return экземпляр, удаленный и полученный из хранилища
     */
    Like delete(Like like);

    /**
     * получение списка всех пользователей поставивших лайк фильму
     * @param filmId идентификатор искомого фильма
     * @return Сет идентификаторов пользователей, поставивших лайк
     */
    Set<Long> getUsersForFilmById(Long filmId);
}