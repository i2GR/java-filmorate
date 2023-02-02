package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.model.entity.Entity;
import ru.yandex.practicum.filmorate.service.EntityServable;
import ru.yandex.practicum.filmorate.storage.entity.EntityStorable;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ControllerTest<T extends Entity> {

    protected EntityStorable<T> storage;
    protected EntityServable<T> service;
    protected BasicController<T> controller;

    @Test
    void addNewNull() {
        assertThrows(RuntimeException.class, () -> controller.addNew(null));
    }

    @Test
    void updateNull() {
        assertThrows(NullPointerException.class, () -> controller.update(null));
    }

    void getAll(T original) {
        assertTrue(controller.getAll().isEmpty());

        controller.addNew(original);

        assertEquals(original, controller.getAll().get(0));
        assertEquals(1, controller.getAll().size());
    }

    protected void addNew(T entity) {
        assertEquals(0, controller.getAll().size());

        try {
            assertEquals(entity, controller.addNew(entity));
        } catch (Exception e) {
            fail();
        }

        assertEquals(1, controller.getAll().size());
    }

    protected void update(T original, T updated) {
        controller.addNew(original);

        assertEquals(updated, controller.update(updated));
        assertEquals(1, controller.getAll().size());
    }
}