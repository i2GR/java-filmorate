package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.user.InMemoryUserService;
import ru.yandex.practicum.filmorate.service.user.UserServable;
import ru.yandex.practicum.filmorate.storage.activity.friends.InMemoryFriendPairsStorage;
import ru.yandex.practicum.filmorate.storage.entity.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.TestUserBuilder;

import java.time.LocalDate;

class UserControllerTest extends ControllerTest<User>{

    private User user;

    private UserControllerTest() {
        super();
        storage = new InMemoryUserStorage();
        service = new InMemoryUserService(new InMemoryFriendPairsStorage(), (UserStorage) storage);
        controller = new UserController((UserServable) service);
    }

    @BeforeEach
    void setup() {
        user = User.builder()
                    .id(1L)
                    .name("name")
                    .email("email@host.dom")
                    .login("login")
                    .birthday(LocalDate.EPOCH)
                    .build();
    }

    @Test
    void addNewUser() {
        super.addNew(user);
    }

    @Test
    void updateUser() {
        User updatedUser = new TestUserBuilder()
                .defaultUser()
                .setName("new name")
                .build();

        super.update(user, updatedUser);
    }

    @Test
    @Override
    void updateNull() {
        super.updateNull();
    }

    @Test
    void getAllUsers() {
        super.getAll(user);
    }
}