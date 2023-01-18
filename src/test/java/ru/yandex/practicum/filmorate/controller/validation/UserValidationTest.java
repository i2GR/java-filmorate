package ru.yandex.practicum.filmorate.controller.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidationTest {
    private User user;

    @BeforeEach
    void setupFilm() {
        user = TestInstance.setDefaultTestUser(1);
        try {
            assertEquals(user, UserValidator.validate(user));
        } catch (ValidationException ve){
            fail();
        }
    }

    @Test
    void validateNull() {

        assertThrows(ValidationException.class, () -> UserValidator.validate(null));
    }

    @Test
    void validateEmailNoAT() {

        user.setEmail("noATsymbol");

        assertThrows(ValidationException.class, () -> UserValidator.validate(user));
    }

    @Test
    void validateLogin() {

        user.setLogin("");

        assertThrows(ValidationException.class, () -> UserValidator.validate(user));
    }

    @Test
    void validateLoginSpaceOnly() {

        user.setLogin(" ");

        assertThrows(ValidationException.class, () -> UserValidator.validate(user));
    }

    @Test
    void validateLoginSpaceInside() {

        user.setLogin("a b");

        assertThrows(ValidationException.class, () -> UserValidator.validate(user));
    }

    @Test
    void validateBirthdayInFuture() {
        LocalDate today = LocalDate.now();
        user.setBirthday(today.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        assertThrows(ValidationException.class, () -> UserValidator.validate(user));
    }

    @Test
    void SubstitutionNameTest() throws ValidationException {
        user.setName("");

        assertEquals(user.getLogin(), UserValidator.validate(user).getName());
    }
}
