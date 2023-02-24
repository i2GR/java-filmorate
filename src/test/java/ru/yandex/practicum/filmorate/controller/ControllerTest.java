package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.entity.Entity;
import ru.yandex.practicum.filmorate.storage.entity.EntityStorable;
import ru.yandex.practicum.filmorate.BasicModelHandling;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ControllerTest<T extends Entity> {

    protected EntityStorable<T> storage;
    protected BasicModelHandling<T> service;
    protected BasicModelHandling<T> controller;

    @Test
    void addNewNull() {
        assertThrows(RuntimeException.class, () -> controller.create(null));
    }

    @Test
    void updateNull() {
        assertThrows(NullPointerException.class, () -> controller.update(null));
    }

    void getAll(T original) {
        assertTrue(controller.readAll().isEmpty());

        controller.create(original);

        assertEquals(original, controller.readAll().get(0));
        assertEquals(1, controller.readAll().size());
    }

    protected void addNew(T entity) {
        assertEquals(0, controller.readAll().size());

        try {
            assertEquals(entity, controller.create(entity));
        } catch (Exception e) {
            fail();
        }

        assertEquals(1, controller.readAll().size());
    }

    protected void update(T original, T updated) {
        controller.create(original);

        assertEquals(updated, controller.update(updated));
        assertEquals(1, controller.readAll().size());
    }
}