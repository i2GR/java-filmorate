package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Genre {
    @NonNull
    private final Integer id;
    private final String name;
}