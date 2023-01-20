package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.ItemForTest;
import ru.yandex.practicum.filmorate.controller.validation.FixedValues;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {
    private static Validator validator;
    private static Film film;

    @BeforeAll
    static void setupValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setupFilm() {
        film = ItemForTest.setDefaultTestFilm(1);
    }

    @Test
    void filmIsValid() {

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );

        assertEquals( 0, constraintViolations.size() );
    }

    @Test
    void nameAndDescriptionNull() {
        film.setName(null);
        film.setDescription(null);

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );
        List<String> violationMessages = constraintViolations.stream().map(v -> v.getMessage())
                                        .collect(Collectors.toList());

        assertEquals( 2, constraintViolations.size() );
        assertTrue(violationMessages.contains("Film.name is blank"));
        assertTrue(violationMessages.contains("Film.description is blank"));
    }

    @Test
    void nameAndDescriptionEmpty() {
        film.setName("");
        film.setDescription("");

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );
        List<String> violationMessages = constraintViolations.stream().map(v -> v.getMessage())
                .collect(Collectors.toList());

        assertEquals( 2, constraintViolations.size() );
        assertTrue(violationMessages.contains("Film.name is blank"));
        assertTrue(violationMessages.contains("Film.description is blank"));

    }

    @Test
    void nameAndDescriptionSpace() {
        film.setName(" ");
        film.setDescription(" ");

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );
        List<String> violationMessages = constraintViolations.stream().map(v -> v.getMessage())
                .collect(Collectors.toList());

        assertEquals( 2, constraintViolations.size() );
        assertTrue(violationMessages.contains("Film.name is blank"));
        assertTrue(violationMessages.contains("Film.description is blank"));
    }

    @Test
    void nameDescriptionSize200() {
        film.setDescription("-".repeat(200));

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );
        List<String> violationMessages = constraintViolations.stream().map(v -> v.getMessage())
                .collect(Collectors.toList());

        assertEquals( 0, constraintViolations.size() );
    }

    @Test
    void nameDescriptionSize201() {
        film.setDescription("-".repeat(201));

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );

        assertEquals( 1, constraintViolations.size() );
        assertEquals( "Film.description too long", constraintViolations.iterator().next().getMessage() );
    }

    @Test
    void durationZero() {
        film.setDuration(0);

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );

        assertEquals( 1, constraintViolations.size() );
        assertEquals( "Film.duration is zero or negative", constraintViolations.iterator().next().getMessage() );
    }

    @Test
    void durationNegative() {
        film.setDuration(-1);

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );

        assertEquals( 1, constraintViolations.size() );
        assertEquals( "Film.duration is zero or negative", constraintViolations.iterator().next().getMessage() );
    }

    @Test
    void releaseDateIsCinemaBirthday() {
        film.setReleaseDate(FixedValues.CINEMA_BIRTHDAY.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );

        assertEquals( 0, constraintViolations.size() );
    }
    @Test
    void releaseDateIsBeforeCinemaBirthday() {
        film.setReleaseDate("1895-12-27");

        Set<ConstraintViolation<Film>> constraintViolations = validator.validate( film );

        assertEquals( 1, constraintViolations.size() );
        assertEquals(  "Film.releaseDate before cinema era", constraintViolations.iterator().next().getMessage() );
    }
}