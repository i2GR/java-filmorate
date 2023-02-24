package ru.yandex.practicum.filmorate.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * класс-шаблон для реализации интерфейса получения сущностей по идентификатору
 * ТЗ-9
 */
public abstract class Entity implements Identable {

    @Getter
    @Setter
    private Long id;
}