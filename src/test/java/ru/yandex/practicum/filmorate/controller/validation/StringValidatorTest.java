package ru.yandex.practicum.filmorate.controller.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertThrows(ValidationException.class, () -> checkEmpty(nullString));
        assertThrows(ValidationException.class, () -> checkEmpty(emptyString));
        assertThrows(ValidationException.class, () -> checkEmpty(oneSpaceString));
        assertThrows(ValidationException.class, () -> checkEmpty(oneSpaceString));
        try {
            assertTrue(checkEmpty(normalString));
        } catch (ValidationException ve) {
            fail();
        }
    }

    @Test
    void checkLength() {
        assertThrows(ValidationException.class, () -> checkLength(oneSpaceString, 0));
        assertThrows(ValidationException.class, () -> checkLength(normalString, normalString.length() - 1));

        try {
            assertTrue(checkLength(emptyString, 0));
            assertTrue(checkLength(emptyString, 1));
            assertTrue(checkLength(normalString, normalString.length() + 1));
        } catch (ValidationException ve) {
            fail();
        }
    }

    private boolean checkEmpty(String param) throws ValidationException{
         return StringValidator.checkEmpty(param, testFieldValue);
    }

    private boolean checkLength(String param, int limit) throws ValidationException{
            return StringValidator.checkLength(param.length(), limit, testFieldValue);
    }
}