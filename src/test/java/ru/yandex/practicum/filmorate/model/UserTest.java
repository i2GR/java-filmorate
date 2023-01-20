package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.ItemForTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static Validator validator;
    private static User user;

    @BeforeAll
    static void setupValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setupUser() {
        user = ItemForTest.setDefaultTestUser(1);
    }

    @Test
    void userIsValid() {

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user );

        assertEquals( 0, constraintViolations.size() );
    }

    @Test
    void loginNull() {
        user.setLogin(null);

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user);
        List<String> violationMessages = constraintViolations.stream().map(v -> v.getMessage())
                .collect(Collectors.toList());

        assertEquals( 1, constraintViolations.size() );
        assertTrue(violationMessages.contains("User.login is null"));
    }
    @Test
    void loginBlank() {
        user.setLogin("");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user);
        List<String> violationMessages = constraintViolations.stream().map(v -> v.getMessage())
                .collect(Collectors.toList());

        assertEquals( 1, constraintViolations.size() );
        assertTrue(violationMessages.contains("User.login contains non letter o digit symbols"));
    }

    @Test
    void loginSpace() {
        user.setLogin(" ");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user);
        List<String> violationMessages = constraintViolations.stream().map(v -> v.getMessage())
                .collect(Collectors.toList());

        assertEquals( 1, constraintViolations.size() );
        assertTrue(violationMessages.contains("User.login contains non letter o digit symbols"));
    }

    @Test
    void BirthDayNow() {
        user.setBirthday(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user);
        List<String> violationMessages = constraintViolations.stream().map(v -> v.getMessage())
                .collect(Collectors.toList());

        assertEquals( 0, constraintViolations.size() );
    }

    @Test
    void BirthDayFuture() {
        user.setBirthday(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user);
        List<String> violationMessages = constraintViolations.stream().map(v -> v.getMessage())
                .collect(Collectors.toList());

        assertEquals( 1, constraintViolations.size() );
        assertTrue(violationMessages.contains("User.birthdate is in Future"));
    }

}