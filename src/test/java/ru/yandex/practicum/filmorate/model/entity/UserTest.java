package ru.yandex.practicum.filmorate.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.utils.TestUserBuilder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Validator validator;
    private User user;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        user = (new TestUserBuilder())
                .defaultUser()
                .build();

    }

    @Test
    void userIsValid() {
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    void loginNull() {
        user.setLogin(null);

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        List<String> violationMessages = constraintViolations
                                        .stream()
                                        .map(ConstraintViolation::getMessage)
                                        .collect(Collectors.toList());

        assertEquals(1, constraintViolations.size());
        assertTrue(violationMessages.contains("User.login is null"));
    }
    @Test
    void loginBlank() {
        user.setLogin("");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        List<String> violationMessages = constraintViolations
                                        .stream()
                                        .map(ConstraintViolation::getMessage)
                                        .collect(Collectors.toList());

        assertEquals(1, constraintViolations.size());
        assertTrue(violationMessages.contains("User.login contains non letter or digit symbols"));
    }

    @Test
    void loginSpace() {
        user.setLogin(" ");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        List<String> violationMessages = constraintViolations
                                        .stream()
                                        .map(ConstraintViolation::getMessage)
                                        .collect(Collectors.toList());

        assertEquals(1, constraintViolations.size());
        assertTrue(violationMessages.contains("User.login contains non letter or digit symbols"));
    }

    @Test
    void BirthDayAtCurrentDate() {
        user.setBirthday(LocalDate.now());

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertEquals(0, constraintViolations.size());
    }

    @Test
    void BirthDayFuture() {
        user.setBirthday(LocalDate.now()
                        .plusDays(1));

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        List<String> violationMessages = constraintViolations
                                        .stream()
                                        .map(ConstraintViolation::getMessage)
                                        .collect(Collectors.toList());

        assertEquals(1, constraintViolations.size());
        assertTrue(violationMessages.contains("User.birthdate is in Future"));
    }
}