package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
public class User {
    @EqualsAndHashCode.Exclude
    private int id;
    private String email;
    private String login;
    private String name;
    private String birthday;
}
