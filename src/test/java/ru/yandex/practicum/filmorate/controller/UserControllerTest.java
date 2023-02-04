package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.activity.friends.FriendsStorable;
import ru.yandex.practicum.filmorate.storage.activity.friends.InMemoryFriendPairsStorage;
import ru.yandex.practicum.filmorate.storage.entity.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.TestUserBuilder;

class UserControllerTest extends ControllerTest<User>{

    private User user;

    private UserControllerTest() {
        super();
        storage = new InMemoryUserStorage();
        service = new UserService(new InMemoryFriendPairsStorage(), (UserStorage) storage);
        controller = new UserController(service);
    }

    @BeforeEach
    void setup() {
        TestUserBuilder builder = new TestUserBuilder();
        user = builder.defaultUser().build();
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