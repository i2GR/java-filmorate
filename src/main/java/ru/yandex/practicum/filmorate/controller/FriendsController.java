package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class FriendsController {

    @NonNull
    @Qualifier("userDBStorage")
    private final FriendServable service;

    /**
     * добавление в друзья
     * @param ownerId идентификатор добавляющего пользователя
     * @param friendId идентификатор пользователя-"друга"
     * @implNote ТЗ-11: пользователь с ownerId получает пользователя с friendId в список друзей
     * при этом у пользователя с friendId в списке друзей не будет пользователя с ownerId
     * @return объект со статусом дружбы
     */
    @PutMapping("/{id}/friends/{friendId}")
    public FriendPair joinUpFriends(@PathVariable("id") Long ownerId, @PathVariable Long friendId) {
        log.info("friends request from user {} (initiator) to user {} (friend to add)", ownerId, friendId);
        return service.joinUpFriends(ownerId, friendId);
    }

    /**
     * удаление из друзей
     * @param ownerId идентификатор для которого удаляется пользователь -"друг"
     * @param friendId идентификатор пользователя-"друга"
     * @return объект со статусом дружбы
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public FriendPair breakFriends(@PathVariable("id") Long ownerId, @PathVariable Long friendId) {
        log.info("break request from user {} to user {}", ownerId, friendId);
        return service.breakFriends(ownerId, friendId);
    }

    /**
     * список пользователей, являющихся его друзьями
     * @param id дентификатор пользователя список друзей которого формируется
     * @return список друзей для данного пользователя
     */
    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Long id) {
        log.info("all-friends request for user {}", id);
        return service.getAllFriends(id);
    }

    /**
     * список друзей, общих с другим пользователем
     * @param id идентификатор пользователя, условный номер 1
     * @param otherId идентификатор пользователя, условный номер 2
     * @return список друзей общих для пользователей условный номер 1 и условный номер 2
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("common-friends request for users {} {}", id, otherId);
        return service.getMutualFriends(id, otherId);
    }
}