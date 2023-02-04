package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.friend.FriendServable;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class FriendsContoller {

    @Autowired
    @NonNull
    private final FriendServable service;

    @PutMapping("/{id}/friends/{friendId}")
    public FriendPair joinUpFriends(@PathVariable Long id, @PathVariable Long friendId) {
        return service.joinUpFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public FriendPair breakFriends(@PathVariable Long id, @PathVariable Long friendId) {
        return service.breakFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Long id) {
        return service.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getAllFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return service.getMutualFriends(id, otherId);
    }
}
