package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
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
@Primary
@Repository("userDBStorage")
@RequiredArgsConstructor
public class DBUserStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> create(User user) {
        String sqlQuery = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
        return insertUserToDB(sqlQuery, user);
    }


    @Override
    public Optional<User> readById(Long entityId) {
        String sqlQuery = "SELECT * FROM users WHERE id = ?";
        return readUserFromDB(sqlQuery, entityId);
    }

    @Override
    public Optional<User> update(User entity) {
        String sqlQuery = "UPDATE users SET "
                          + "email = ?, login = ? , name = ?, birthday = ? "
                          + "WHERE id = ?";
        return updateUserInDB(sqlQuery, entity);
    }

    @Override
    public List<User> readAll() {
        String sqlQuery = "SELECT * FROM users";
        return readAllUsersFromDB(sqlQuery);
    }


    @Override
    public boolean delete(Long id) {
        String sqlQuery = "DELETE FROM users WHERE id = ?";
        return deleteUserFromDB(sqlQuery, id);
    }

    private Optional<User> insertUserToDB(String sqlQuery, User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                        PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
                        stmt.setString(1, user.getEmail());
                        stmt.setString(2, user.getLogin());
                        stmt.setString(3, user.getName());
                        stmt.setDate(4, Date.valueOf(user.getBirthday()));
                        return stmt;
                    }
                    , keyHolder);
            Long newUserId = keyHolder.getKey().longValue();
            user.setId(newUserId);
            return Optional.of(user);
        } catch (DataAccessException dae) {
            // empty: goto return;
        } catch (NullPointerException npe) {
            // empty: goto return;
        }
        return Optional.empty();
    }

    private Optional<User> updateUserInDB(String sqlQuery, User user){
        try {
            int affectedRows = jdbcTemplate.update( sqlQuery
                                                    , user.getEmail()
                                                    , user.getLogin()
                                                    , user.getName()
                                                    , user.getBirthday()
                                                    , user.getId());
            if (affectedRows == 1) {
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    private Optional<User> readUserFromDB (String sqlQuery, Long id) {
        try {
            User user = jdbcTemplate.queryForObject(sqlQuery, new UserRowMapper(), id);
            return Optional.of(user);
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    private List<User> readAllUsersFromDB(String sqlQuery) {
        return jdbcTemplate.query(sqlQuery, new UserRowMapper());
    }

    private boolean deleteUserFromDB(String sqlQuery, Long id) {
        try{
            return jdbcTemplate.update(sqlQuery, id) == 1;
        } catch (DataAccessException dae) {
            return false;
        }
    }
}
