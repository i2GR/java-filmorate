package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;

public abstract class Entity implements Identable {
    @Getter
    @Setter
    private int id;
}
