package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.friend.FriendServable;

import java.util.List;

/**
 * реализация  базового CRUD-функционала ендпойнтов для статуса друзей
 * ТЗ-10
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class FriendsContoller {

    @Autowired
    @NonNull
    private final FriendServable service;

    @PutMapping("/{id}/friends/{friendId}")
    public FriendPair joinUpFriends(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("friends request from user {} to user {}", id, friendId);
        return service.joinUpFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public FriendPair breakFriends(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("break request from user {} to user {}", id, friendId);
        return service.breakFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Long id) {
        log.info("all-friends request for user {}", id);
        return service.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getAllFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("common-friends request for users {} {}", id, otherId);
        return service.getMutualFriends(id, otherId);
    }
}
