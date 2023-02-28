package ru.yandex.practicum.filmorate;

import ru.yandex.practicum.filmorate.model.entity.Entity;

import java.util.List;

/**
 * ТЗ-10 <p>
 * Интерфейс-шаблон для хранилища идентифицируемых моделей <p>
 * Предусмаривается подход CRUD <p>
 * Подразумевается использование идентификатора <p>
 * @implNote <u>"идентифицируемая модель"</u> в описании методов это экземпляр класса-наследника {@link Entity} <p>
 * @param <T> указанный экземпляр класса, имеющий поле и сеттер/геттер идентификатора
 */
public interface BasicModelHandling<T extends Entity> {

    /**
     * Сохранение экземпляра фильма или пользователя
     * @param entity  экземпляр дял сохранения
     * @return сохраненный экземпляр
     */
    T create(T entity);

    /**
     * Получение фильма или пользователя из хранилища по идентификатору
     * @param entityId присвоенный идентификатор фильма или пользователя
     * @return экземпляр фильма или пользователя, полученный по идентификатору
     */
    T readById(Long entityId);

    /**
     * Модификация  фильма или пользователя в хранилище
     * @param entity новый экземпляр фильма или пользователя
     * @return экземпляр фильма или пользователя
     */
    T update(T entity);

    /**
     * Получение списка всех фильмов или пользователей
     * @return список
     */
    List<T> readAll();

/*   /**
     * вспомогательный метод извлечения экземпляра класса пользователя
     * для удаления повторяющегося кода
     * @param optional Optional<T>, полученный из хранилища (БД)
     * @param exceptionMessage сообщение, подходящее из контекста вызывающего метода
     * @return экземпляр класса пользователя (в случае отсутствия ошибок получения данных из БД)
     *//*
    default T getFromOptionalOrThrowException(Optional<T> optional, String exceptionMessage) {
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new StorageNotFoundException(exceptionMessage);
        }
    }*/
}