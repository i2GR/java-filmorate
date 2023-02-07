package ru.yandex.practicum.filmorate.storage.entity;

import ru.yandex.practicum.filmorate.BasicModelHandling;
import ru.yandex.practicum.filmorate.model.entity.Entity;

/**
 * ТЗ-10 <p>
 * Интерфейс-шаблон для хранилища идентифицируемых моделей <p>
 * Предусмаривается подход CRUD <p>
 * Подразумевается использование идентификатора <p>
 * @implNote <u>"идентифицируемая модель"</u> в описании методов это экземпляр класса-наследника {@link Entity} <p>
 * @param <T> указанный экземпляр класса, имеющий поле и сеттер/геттер идентификатора
 */

public interface EntityStorable<T extends Entity> extends BasicModelHandling<T> {

    /**
     * ТЗ-10 <p>
     * Удаление экземпляра фильма или пользователя.
     * метод согласно пункту ТЗ "Архитектура"
     * @implNote Не используется в сервис-классаах и контроллер классах, т.к. не определены ендпойнты по ТЗ
     * @param id идентификатор экземпляра entity для удаления
     * @return экземпляр, удаленный и полученный из хранилища
     */
    T delete(Long id);
}