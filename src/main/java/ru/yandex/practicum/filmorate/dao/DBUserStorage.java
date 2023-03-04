package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.UserRowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

/**
 * реализация функционала репозитория базы данных пользователей
 * ТЗ-11
 */
@Slf4j
@Primary
@Repository("userDBStorage")
@RequiredArgsConstructor
public class DBUserStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> create(User user) {
        String sqlQuery = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?);";
        log.info("SQL request for User create id");
        return insertUserToDB(sqlQuery, user);
    }

    @Override
    public Optional<User> readById(Long userId) {
        String sqlQuery = "SELECT * FROM users WHERE id = ?;";
        log.info("SQL request for user read id {}", userId);
        return readUserFromDB(sqlQuery, userId);
    }

    @Override
    public Optional<User> update(User user) {
        String sqlQuery = "UPDATE users SET "
                + "email = ?, login = ? , name = ?, birthday = ? "
                + "WHERE id = ?";
        log.info("SQL request for User update id {}", user.getId());
        return updateUserInDB(sqlQuery, user);
    }

    @Override
    public List<User> readAll() {
        String sqlQuery = "SELECT * FROM users;";
        log.info("SQL request for All-Users List");
        return readAllUsersFromDB(sqlQuery);
    }

    @Override
    public boolean delete(Long id) {
        String sqlQuery = "DELETE FROM users WHERE id = ?;";
        log.info("SQL request for User delete with id {}", id);
        return deleteUserFromDB(sqlQuery, id);
    }

    private Optional<User> insertUserToDB(String sqlQuery, User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            int affectedRow = jdbcTemplate.update(connection -> {
                        PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
                        stmt.setString(1, user.getEmail());
                        stmt.setString(2, user.getLogin());
                        stmt.setString(3, user.getName());
                        stmt.setDate(4, Date.valueOf(user.getBirthday()));
                        return stmt;
                    }
                    , keyHolder);
            log.info("DB User create ({} record made)", affectedRow);
            Number n = keyHolder.getKey();
            if (n != null) {
                Long newUserId = n.longValue();
                user.setId(newUserId);
                log.info("OK inserting Film to DB with new id {}", newUserId);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (DataAccessException dae) {
            log.error("DB DataAccessException creating User");
            return Optional.empty();
        }
    }

    private Optional<User> updateUserInDB(String sqlQuery, User user) {
        try {
            int affectedRows = jdbcTemplate.update(sqlQuery
                    , user.getEmail()
                    , user.getLogin()
                    , user.getName()
                    , user.getBirthday()
                    , user.getId());
            if (affectedRows == 1) {
                log.info("OK User updating in DB");
                return Optional.of(user);
            }
            log.error("Error updating Data in DB: not One record affected");
            return Optional.empty();
        } catch (DataAccessException dae) {
            log.error("DB DataAccessException updating User");
            return Optional.empty();
        }
    }

    private Optional<User> readUserFromDB(String sqlQuery, Long id) {
        try {
            User user = jdbcTemplate.queryForObject(sqlQuery, new UserRowMapper(), id);
            log.info("OK user reading from DB");
            return Optional.ofNullable(user);
        } catch (DataAccessException dae) {
            log.info("DB DataAccessException reading User");
            return Optional.empty();
        }
    }

    private List<User> readAllUsersFromDB(String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, new UserRowMapper());
    }

    private boolean deleteUserFromDB(String sqlQuery, Long id) {
        try {
            int affectedRows = jdbcTemplate.update(sqlQuery, id);
            if (affectedRows == 1) {
                log.info("OK deleting User for DB.{} User deleted, id {}", affectedRows, id);
                return true;
            }
        } catch (DataAccessException dae) {
            log.info("DB DataAccessException deleting User with id {}", id);
        }
        return false;
    }
}
