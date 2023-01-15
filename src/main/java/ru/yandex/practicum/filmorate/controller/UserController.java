package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    public User addNewUser(@RequestBody User user){
        //TODO
        return user;
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user){
        //TODO
        return user;
    }

    @GetMapping("/list")
    public List<User> getAllUsers(){
        log.info("INFO getAllUsers");
        log.debug("DEBUG");
        return new ArrayList<>(idMapUser.values());
    }
}
