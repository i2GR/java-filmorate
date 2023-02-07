package ru.yandex.practicum.filmorate.model.entity;

/** интерфейс получения сущностей по идентификатору
 * ТЗ-9
 */
public interface Identable {

    void setId(Long id);

    Long getId();
}