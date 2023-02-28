package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.BasicModelHandling;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.user.UserServable;

import javax.validation.Valid;
import java.util.List;

/**
 * реализация  базового CRUD-функционала ендпойнтов для пользователей
 * ТЗ-9
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController implements BasicModelHandling<User> {

    @NonNull
    private final UserServable userService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("add user request");
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public User readById(@Valid @PathVariable Long id) {
        log.info("get user request with id {}", id);
        return userService.readById(id);
    }

    @PutMapping
    public User update(@NonNull @Valid @RequestBody User user) {
        log.info("update user request with id {}", user.getId());
        return userService.update(user);
    }

    @GetMapping
    public List<User> readAll() {
        log.info("get all users request");
        return userService.readAll();
    }

}