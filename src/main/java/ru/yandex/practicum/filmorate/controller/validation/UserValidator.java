package ru.yandex.practicum.filmorate.controller.validation;

import ru.yandex.practicum.filmorate.model.User;

public class UserValidator {

    public static User validate(User user) throws ValidationException {
        StringValidator.checkEmpty(user.getEmail(), "User.email");
        StringValidator.checkEmpty(user.getLogin(), "User.login");
        checkEmail(user.getEmail());
        checkLogin(user.getLogin());
        user.setName(checkName(user));
        return user;
    }

    private static void checkEmail(String email) throws ValidationException {

        if (!email.contains("@")) {
            throw new ValidationException("user.email does not contain '@'");
        }
    }
    private static void checkLogin(String login) throws ValidationException {
        if (login.contains(" ")) {
            throw new ValidationException("user.login contain space character");
        }
    }

    private static String checkName(User user) {
        return (user.getName().isBlank()) ? user.getEmail() : user.getName();
    }

}
