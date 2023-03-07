package ru.yandex.practicum.filmorate.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DBFriendStorage;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;

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
    private DBFriendStorage friendDBStorage;

    @Override
    public FriendPair joinUpFriends(Long initiatorId, Long friendId) {
        Optional<FriendPair> friendsOptional = friendDBStorage.create(initiatorId, friendId);
        log.info("received data from DB: {}", friendsOptional.isPresent());
        return friendsOptional.orElseThrow();
    }

    @Override
    public FriendPair breakFriends(Long initiatorId, Long friendId) {
        Optional<FriendPair> friendsOptional = friendDBStorage.delete(initiatorId, friendId);
        log.info("received data from DB: {}", friendsOptional.isPresent());
        return friendsOptional.orElseThrow();
    }
}