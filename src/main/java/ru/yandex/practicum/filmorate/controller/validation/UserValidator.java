package ru.yandex.practicum.filmorate.controller.validation;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class UserValidator  {

    public static User validate(User user) throws ValidationException {
        try {
            nullCheck(user);
            StringValidator.checkEmpty(user.getEmail(), "User.email");
            checkEmail(user.getEmail());
            StringValidator.checkEmpty(user.getLogin(), "User.login");
            checkLogin(user.getLogin());
            checkBirthday(LocalDate.parse(user.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } catch (ValidationException ve) {
            log.warn(ve.getMessage());
            throw ve;
        }
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
        return (user.getName() == null || user.getName().isBlank()) ? user.getLogin() : user.getName();
    }

    private static void checkBirthday(LocalDate date) throws ValidationException {
        if (date.isAfter(LocalDate.now())) throw new ValidationException("incorrect birthday with future value");
    }

    private static void nullCheck(User user) throws ValidationException{
        if (user == null) throw new ValidationException("null User received");
    }

}
