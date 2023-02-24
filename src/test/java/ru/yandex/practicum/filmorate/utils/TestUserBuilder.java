package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.model.entity.User;

import java.time.LocalDate;

public class TestUserBuilder {

    private final User user = new User();

    public TestUserBuilder defaultUser () {
        setId(1L);
        setName("name");
        setEmail("email@host.dom");
        setLogin("login");
        setBirthday(LocalDate.EPOCH);
        return this;
    }

    public TestUserBuilder setId(Long id) {
        user.setId(id);
        return this;
    }

    public TestUserBuilder setName(String name) {
       user.setName(name);
       return this;
    }

    public TestUserBuilder setEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public TestUserBuilder setLogin(String login) {
        user.setLogin(login);
        return this;
    }

    public TestUserBuilder setBirthday(LocalDate birthday) {
        user.setBirthday(birthday);
        return this;
    }

    public User build() {
        return user;
    }
}
