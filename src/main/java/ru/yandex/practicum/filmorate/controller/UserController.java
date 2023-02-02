package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.EntityServable;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends BasicController<User> {

    public UserController(EntityServable<User> service) {
        super(service);
    }

    @Override
    @PostMapping
    public User addNew(@Valid @RequestBody User user) {
        return super.addNew(user);
    }

    @Override
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Override
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return super.update(user);
    }

    @Override
    @GetMapping
    public List<User> getAll() {
        return super.getAll();
    }

}