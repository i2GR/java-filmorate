package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.StorageDuplicateException;
import ru.yandex.practicum.filmorate.exception.StorageNotFoundException;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.storage.activity.friends.FriendsStorable;
import ru.yandex.practicum.filmorate.utils.UserRowMapper;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

/**
 * реализация функционала репозитория базы данных при работе со статусаами друзей пользователей
 * ТЗ-11
 */
@Slf4j
@Repository("friendDBStorage")
@RequiredArgsConstructor
public class DBFriendStorage implements FriendsStorable {

    private final JdbcTemplate jdbcTemplate;

    /**
     * реализация с проверкой наличия пользователей в хранилище
     * @implNote ТЗ-11: <p>пользователь с ownerId получает пользователя с friendId в список друзей
     * при этом у пользователя с friendId в списке друзей не будет пользователя с ownerId
    */
    @Override
    public Optional<FriendPair> create(@Valid Long ownerId, @Valid Long friendId) {
        String sqlQuery = "INSERT INTO friends (owner_id, friend_id) VALUES (?, ?);";
        log.info("SQL request for put friends status  users id: {} to {}", ownerId, friendId);
        return updateFriendForOwner(sqlQuery, ownerId, friendId);
    }

    @Override
    public Optional<FriendPair> delete(@Valid Long ownerId, @Valid Long friendId) {
        String sqlQuery = "DELETE FROM friends WHERE owner_id = ? AND  friend_id = ?;";
        log.info("SQL request for delete friends status  users id: {} to {}", ownerId, friendId);
        return updateFriendForOwner(sqlQuery, ownerId, friendId);
    }

    public List<User> getAllFriends(@Valid Long id) {
        String sqlQuery = "SELECT * "
                        + "FROM users AS u "
                        + "INNER JOIN friends AS f ON f.friend_id = u.id "
                        + "WHERE f.owner_id = ?;";
        log.info("SQL request for get friends-list for user id {}", id);
        return jdbcTemplate.query(sqlQuery, new UserRowMapper(), id);
    }

    private Optional<FriendPair> updateFriendForOwner(String sqlQuery, Long ownerId, Long friendId) {
        try {
            int affectedRows = jdbcTemplate.update(sqlQuery, ownerId, friendId);
            if (affectedRows == 1) {
                log.info("update One record in DB for friends status");
                return Optional.of(new FriendPair(ownerId, friendId));
            }
            throw new StorageDuplicateException(
                                String.format("duplicated data in DB for friends-owner with id %d and friend with id %d"
                                , ownerId, friendId));
        } catch (Exception e) {
            throw new StorageNotFoundException(
                                String.format("error searching for found DB record with id %d for user id %d"
                                , friendId, ownerId));
        }
    }
}