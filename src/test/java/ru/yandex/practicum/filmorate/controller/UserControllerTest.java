package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.TestUserBuilder;

class UserControllerTest extends ControllerTest<User>{

    private User user;

    private UserControllerTest() {
        super(new UserController());
    }

    @BeforeEach
    void setup() {
        TestUserBuilder builder = new TestUserBuilder();
        user = builder.defaultUser().build();
    }

    @Test
    void addNewMovie() {
        super.addNew(user);
    }

    @Test
    void updateMovie() {
        User updatedUser = new TestUserBuilder()
                                .defaultUser()
                                .setName("new name")
                                .build();

        super.update(user, updatedUser);
    }

    @Test
    void updateNullMovie() {
        super.updateNull();
    }

    @Test
    void getAllMovies() {
        super.getAll(user);
    }
}