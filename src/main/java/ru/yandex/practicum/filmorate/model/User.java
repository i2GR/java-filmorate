package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.controller.validation.BirthDayPast;

import javax.validation.constraints.*;

@Data
public class User {
    @EqualsAndHashCode.Exclude
    private int id;

    @Email
    private String email;

    @NotNull(message = "User.login is null")
    @Pattern(regexp = "^\\w{1,}$", message = "User.login contains non letter o digit symbols")
    private String login;

    private String name;

    @BirthDayPast
    private String birthday;


}
