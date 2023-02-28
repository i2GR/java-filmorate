package ru.yandex.practicum.filmorate.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DBFriendsStorage;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * реализация ервис-слоя CRUD-функционала для статуса друзей
 */
@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class DBFriendService implements FriendServable {

    @NonNull
    @Autowired
    private DBFriendsStorage storage;

    @Override
    public FriendPair joinUpFriends(Long initiatorId, Long friendId) {
        Optional<FriendPair> friendsOptional = storage.create(initiatorId, friendId);
        log.info("received data from DB: {}", friendsOptional.isPresent());
        return friendsOptional.orElseThrow();
    }

    @Override
    public FriendPair breakFriends(Long initiatorId, Long friendId) {
        Optional<FriendPair> friendsOptional = storage.delete(initiatorId, friendId);
        log.info("received data from DB: {}", friendsOptional.isPresent());
        return friendsOptional.orElseThrow();
    }

    @Override
    public List<User> getMutualFriends(Long userId1, Long userId2) {
        List<User> mutualList = storage.getMutualFriends(userId1, userId2);
        log.info("received data from DB: {}", mutualList.size());
        return mutualList;
    }

    @Override
    public List<User> getAllFriends(Long id) {
        List<User> friendList = storage.getAllFriends(id);
        log.info("received data from DB: {}", friendList.size());
        return friendList;
    }
}