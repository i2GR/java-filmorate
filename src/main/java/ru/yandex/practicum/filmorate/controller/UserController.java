package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.controller.validation.UserValidator;
import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private int id = 0;
    private final Map<Integer, User> idMapUser = new HashMap<>();

    @PostMapping("")
    public User addNewUser(@RequestBody User user) throws ValidationException {
        log.info("requested user add");
        user = UserValidator.validate(user);
        if (user.getId() == 0) {
            user.setId(++id);
        }
        if (!idMapUser.containsKey(user.getId())) {
            idMapUser.put(user.getId(), user);
            log.info("user added");
            return user;
        }
        log.info("user not added. duplicated");
        throw new ValidationException("user not added. duplicated");
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) throws ValidationException {
        log.info("requested user update ");
        user = UserValidator.validate(user);
        if (idMapUser.containsKey(user.getId())) {
            idMapUser.remove(user.getId());
            idMapUser.put(user.getId(), user);
            log.info("user updated");
            return user;
        }
        log.info("user not updated. no movie found");
        throw new ValidationException("user not updated. no user found");
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        log.info("requested users list");
        return new ArrayList<>(idMapUser.values());
    }
}