package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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
@Repository("friendsDBStorage")
@RequiredArgsConstructor
public class DBFriendsStorage implements FriendsStorable {

    private final JdbcTemplate jdbcTemplate;

    /**
     * реализация с проверкой наличия пользователей в хранилище
     * @implNote ТЗ-11: <p>пользователь с ownerId получает пользователя с friendId в список друзей
     * при этом у пользователя с friendId в списке друзей не будет пользователя с ownerId
    */
    @Override
    public Optional<FriendPair> create(@Valid Long ownerId, @Valid Long friendId) {
        String sqlQuery = "INSERT INTO friends (owner_id, friend_id) VALUES (?, ?);";
        return insertNewFriendForOwner(sqlQuery, ownerId, friendId);
    }

    @Override
    public Optional<FriendPair> delete(@Valid Long ownerId, @Valid Long friendId) {
        String sqlQuery = "DELETE FROM friends WHERE owner_id = ? AND  friend_id = ?;";
        return deleteFriendForOwner(sqlQuery, ownerId, friendId);
    }

    public List<User> getMutualFriends(@Valid Long userId1, @Valid Long userId2) {
        String subQuery = "(SELECT friend_id AS mutual "
                            + "FROM friends "
                            + "WHERE owner_id = ? "
                            + "INTERSECT "
                            + "SELECT friend_id "
                            + "FROM friends "
                            + "WHERE owner_id = ?)";
        String sqlQuery = "SELECT * FROM users WHERE id IN " + subQuery + ";";
        return jdbcTemplate.query(sqlQuery, new UserRowMapper(), userId1, userId2);
    }

    public List<User> getAllFriends(@Valid Long id) {
        String subQuery = "(SELECT friend_id "
                + "FROM friends "
                + "WHERE owner_id = ?)";
        String sqlQuery = "SELECT * FROM users WHERE id IN " + subQuery + ";";
        return jdbcTemplate.query(sqlQuery, new UserRowMapper(), id);
    }

    private Optional<FriendPair> insertNewFriendForOwner(String sqlQuery, Long ownerId, Long friendId) {
        try {
            return updateRecord(sqlQuery, ownerId, friendId);
        } catch (DataAccessException e) {
            throw new StorageDuplicateException(
                    String.format("error inserting friend with %d by user id %d", friendId, ownerId));
        }
    }

    private Optional<FriendPair> deleteFriendForOwner(String sqlQuery, Long ownerId, Long friendId) {
        try {
            return updateRecord(sqlQuery, ownerId, friendId);
        } catch (DataAccessException e) {
            throw new StorageNotFoundException(
                    String.format("not found friend with id %d for user id %d to delete", friendId, ownerId));
        }
    }

    private Optional<FriendPair> updateRecord(String sqlQuery, Long ownerId, Long friendId) throws DataAccessException{
        if (jdbcTemplate.update(sqlQuery, ownerId, friendId) == 1) {
            return Optional.of(new FriendPair(ownerId, friendId));
        }
        return Optional.empty();
    }
}
