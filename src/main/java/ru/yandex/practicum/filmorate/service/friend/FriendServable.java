package ru.yandex.practicum.filmorate.service.friend;

import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;

import java.util.List;

public interface FriendServable {

    FriendPair joinUpFriends(Long userId1, Long userId2);

    boolean areFriends(Long userId1, Long userId2);

    FriendPair breakFriends(Long userId1, Long userId2);

    List<User> getMutualFriends(Long userId1, Long userId2);

    List<User> getAllFriends(Long id);
}
