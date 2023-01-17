package ru.yandex.practicum.filmorate.controller.validation;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidator {

    public static User validate(User user) throws ValidationException {
        if (user == null) throw new ValidationException("null object received");
        StringValidator.checkEmpty(user.getEmail(), "User.email");
        StringValidator.checkEmpty(user.getLogin(), "User.login");
        checkEmail(user.getEmail());
        checkLogin(user.getLogin());
        user.setName(checkName(user));
        checkBirthday(user.getBirthday());
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
        return (user.getName().isBlank() || user.getName() == null) ? user.getLogin() : user.getName();
    }

    private static void checkBirthday(LocalDate date) throws ValidationException {
        if (date.isAfter(LocalDate.now())) throw new ValidationException("incorrect birthday with future value");
    }

}
