package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.EntityServable;

import javax.validation.Valid;
import java.util.List;

/**
 * реализация  базового CRUD-функционала ендпойнтов для пользователей
 * ТЗ-9
 * @see BasicController
 */
@RestController
@RequestMapping("/users")
public class UserController extends BasicController<User> {

    public UserController(EntityServable<User> service) {
        super(service);
    }

    @Override
    @PostMapping
    public User addNew(@Valid @RequestBody User user) {
        log().info("add user request");
        return super.addNew(user);
    }

    @Override
    @GetMapping("/{id}")
    public User getById(@Valid @PathVariable Long id) {
        log().info("get user request with id {}", id);
        return super.getById(id);
    }

    @Override
    @PutMapping
    public User update(@NonNull @Valid @RequestBody User user) {
        log().info("update user request with id {}", user.getId());
        return super.update(user);
    }

    @Override
    @GetMapping
    public List<User> getAll() {
        log().info("get all users request");
        return super.getAll();
    }

}