package ru.yandex.practicum.filmorate.model.entity;

import lombok.Getter;
import lombok.Setter;

public abstract class Entity implements Identable {
    @Getter
    @Setter
    private Long id;

}
