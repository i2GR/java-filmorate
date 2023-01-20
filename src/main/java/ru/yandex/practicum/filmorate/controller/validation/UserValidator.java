package ru.yandex.practicum.filmorate.controller.validation;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * вадидация пользователя согласно первой части ТЗ (без учета доп.задания)
 * @Note осталось применение только в тестах
 */
public class UserValidator  {

    /**
     *
     * @param user экз. пользователя
     * @return экземпляр пользователя
     * @throws ValidationException пользовательствое иисключение согласно ТЗ
     */
    public static User validate(User user) throws ValidationException {
        if (user == null ) throw new ValidationException("null User received");
        StringValidator.checkEmpty(user.getEmail(), "User.email");
        checkEmail(user.getEmail());
        StringValidator.checkEmpty(user.getLogin(), "User.login");
        checkLogin(user.getLogin());
        checkBirthday(LocalDate.parse(user.getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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
        if (date.isAfter(LocalDate.now())) throw new ValidationException("user.birthday has incorrect value of future value");
    }

}
