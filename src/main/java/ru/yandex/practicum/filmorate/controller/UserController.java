package ru.yandex.practicum.filmorate.controller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import ru.yandex.practicum.filmorate.controller.validation.UserValidator;
import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private int id = 0;

    @Setter
    private boolean customValidation = false;

    private final Map<Integer, User> idMapUser = new HashMap<>();

    @PostMapping("")
    public User addNewUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("requested user add");
        // здесь валидатор остался только для написанных тестов
        if (customValidation) user = UserValidator.validate(user);
        user.setName(user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName());
        if (user.getId() == 0) {
            user.setId(++id);
        }
        if (!idMapUser.containsKey(user.getId())) {
            idMapUser.put(user.getId(), user);
            log.info("user added");
            return user;
        }
        throw new ValidationException("user to add already exists");
    }

    @PutMapping("")
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        log.info("requested user update ");
        // здесь валидатор остался только для написанных тестов
        if (customValidation) user = UserValidator.validate(user);
        user.setName(user.getName() == null || user.getName().isBlank() ? user.getLogin() : user.getName());
        if (idMapUser.containsKey(user.getId())) {
            idMapUser.remove(user.getId());
            idMapUser.put(user.getId(), user);
            log.info("user updated");
            return user;
        }
        throw new ValidationException("user is not registered");
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        log.info("requested users list");
        return new ArrayList<>(idMapUser.values());
    }
}