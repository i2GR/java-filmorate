package ru.yandex.practicum.filmorate.storage.activity;

import ru.yandex.practicum.filmorate.model.activity.ActivityEvent;

import java.util.Set;

/**
 * Интерфейс-шаблон для хранилища объектов активностей пользователей (к текущем варианте лайки и пары друзей)
 * @implNote предусматривается подход CRUD, но без метода Update, т.к. лайк или состояние в друзьях либо есть - либо нет.
 * @see ActivityEvent
 * @param <T> класс ActivityEvent
 */

public interface ActivityStorable<T extends ActivityEvent> {

    /**
     * сохранение
     * @param activity  экземпляр для сохранения
     * @return сохраненный экземпляр
     */
    T create(T activity);

    /**
     * проверка нахождения экземпляра в хранилище
     * @param activity искомое событие экземпляр ActivityEvent
     * @return true, если событие найдено, и false если события нет
     */
    //TODO
    // параметр ENTITY?
    boolean read(T activity);

    /**
     * удаление
     * @implNote метод подразумевает что ActivityEvent должен быть удален по совпадению критериев
     * например, для лайка: и кто поставил лайк и что было пролайкано
     * @param activity событие
     * @return экземпляр, удаленный и полученный из хранилища
     */
    T delete(T activity);

    /**
     * получение списка всех активностей по идентификатору
     * @return Сет активностей ,связанный с запрошенным идентификатором
     * в зависимости от контекста
     */
    Set<Long> getActivitiesById(Long id);

}
