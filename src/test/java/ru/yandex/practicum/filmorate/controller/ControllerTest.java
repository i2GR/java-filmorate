package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Entity;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class ControllerTest<T extends Entity> {

    private final BasicController<T>  controller;

    protected ControllerTest(BasicController<T> controller) {
        this.controller = controller;
    }

    @Test()
    void addNewNull() {
        assertThrows(Exception.class, () -> controller.addNew(null));
    }

    @Test
    void updateNull() {
        assertThrows(NullPointerException.class, () -> controller.update(null));
    }

    void getAll(T original) {
        assertEquals(0, controller.getAll().size());

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