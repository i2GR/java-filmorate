package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.ItemForTest;
import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private final User user = ItemForTest.setDefaultTestUser(1);
    UserController uc = new UserController();

    {
        uc.setCustomValidation(true);
    }

    @Test
    void addNewUser() throws ValidationException {
        assertEquals(user,uc.addNewUser(user));
        assertEquals(1, uc.getAllUsers().size());
        assertThrows(ValidationException.class, () -> uc.addNewUser(user));
        assertThrows(ValidationException.class, () -> uc.addNewUser(null));
    }

    @Test
    void updateUser() throws ValidationException {
        uc.addNewUser(user);
        user.setName("newName");

        assertEquals("newName", uc.updateUser(user).getName());
        assertThrows(ValidationException.class, () -> uc.updateUser(null));
    }

    @Test
    void getAllUser() throws ValidationException {
        uc.addNewUser(user);
        User user2 = ItemForTest.setDefaultTestUser(2);
        user2.setLogin("newLogin");
        uc.addNewUser(user2);

        User[] expected = {user, user2};
        User[] actual = uc.getAllUsers().toArray(new User[]{});

        assertArrayEquals(expected, actual);
    }
}