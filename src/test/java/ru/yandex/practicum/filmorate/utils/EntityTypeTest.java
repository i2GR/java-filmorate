package ru.yandex.practicum.filmorate.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTypeTest {

    @Test
    void value() {
        assertEquals("Film", EntityType.FILM.val());
        assertEquals("User", EntityType.USER.val());
    }
}