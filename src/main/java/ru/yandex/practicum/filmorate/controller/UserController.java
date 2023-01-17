package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.controller.validation.UserValidator;
import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final Map<Integer, User> idMapUser = new HashMap<>();

    @PostMapping("/new")
    public User addNewUser(@RequestBody User user) throws ValidationException {
        log.info("requested user add");
        user = UserValidator.validate(user);
        if (!idMapUser.containsValue(user)) {
            idMapUser.put(user.getId(), user);
            log.info("user added");
            return user;
        }
        log.info("user not added. duplicated");
        return user;
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) throws ValidationException {
        log.info("requested user update ");
        user = UserValidator.validate(user);
        if (idMapUser.containsValue(user)) {
            idMapUser.put(user.getId(), user);
            log.info("user updated");
            return user;
        }
        log.info("user not updated. no user found");
        return user;
    }

    @GetMapping("/list")
    public List<User> getAllUsers() {
        log.info("requested users list");
        return new ArrayList<>(idMapUser.values());
    }
}