package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Primary
@Repository("userDBStorage")
@RequiredArgsConstructor
public class DBUserStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User entity) {
        String sqlQuery = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
        /*int affectedRows = jdbcTemplate.update( sql,
                                                entity.getEmail(),
                                                entity.getLogin(),
                                                entity.getName(),
                                                entity.getBirthday());*/
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, entity.getEmail());
            stmt.setString(2, entity.getLogin());
            stmt.setString(3, entity.getName());
            stmt.setDate(4, Date.valueOf(entity.getBirthday()));
            return stmt;}
            , keyHolder);
        try {
            Long newUserId = keyHolder.getKey().longValue();
            entity.setId(newUserId);
            return entity;
        } catch (NullPointerException npe) {
            return new User();
        }
    }


    @Override
    public User readById(Long entityId) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public User delete(Long id) {
        return null;
    }
}
