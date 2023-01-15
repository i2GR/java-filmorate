package ru.yandex.practicum.filmorate.controller.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {
    String nullString = null;
    String emptyString = "";
    String oneSpaceString = " ";
    String twoSpacesString = "  ";
    String normalString ="some_String Value "; //length = 18
    String testFieldValue = "ClassName:value";
    String exceptionCase = "exceptionThrown";

    @BeforeEach
    void setUp() {
    }

    @Test
    void checkEmpty() {
        assertEquals(checkEmptyWithException(nullString), exceptionCase);
        assertEquals(checkEmptyWithException(emptyString), exceptionCase);
        assertEquals(checkEmptyWithException(oneSpaceString), exceptionCase);
        assertEquals(checkEmptyWithException(twoSpacesString), exceptionCase);
        assertEquals(checkEmptyWithException(normalString), normalString);
    }

    @Test
    void checkLength() {
        assertEquals(checkLengthWithException(emptyString, 0), emptyString);
        assertEquals(checkLengthWithException(emptyString, 1), emptyString);
        assertEquals(checkLengthWithException(oneSpaceString, 0), exceptionCase);
        assertEquals(checkLengthWithException(oneSpaceString, 1), oneSpaceString);
        assertEquals(checkLengthWithException(oneSpaceString, 2), oneSpaceString);
        assertEquals(checkLengthWithException(twoSpacesString, 2), twoSpacesString);
        assertEquals(checkLengthWithException(normalString, normalString.length() + 1), normalString);
        assertEquals(checkLengthWithException(normalString, normalString.length() - 1), exceptionCase);
    }

    private String checkEmptyWithException(String param) {
        try {
            StringValidator.checkEmpty(param, testFieldValue);
            return param;
        } catch (ValidationException ve) {
            return "exceptionThrown";
        }
    }

    private String checkLengthWithException(String param, int limit) {
        try {
            StringValidator.checkLength(param.length(), limit, testFieldValue);
            return param;
        } catch (ValidationException ve) {
            return "exceptionThrown";
        }
    }
}