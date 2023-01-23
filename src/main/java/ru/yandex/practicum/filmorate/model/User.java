package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=false)
public class User extends Entity {
    @Email
    private String email;

    @NotNull(message = "User.login is null")
    @Pattern(regexp = "^\\w+$", message = "User.login contains non letter or digit symbols")
    private String login;

    private String name;

    @PastOrPresent(message = "User.birthdate is in Future")
    LocalDate birthday;

}
