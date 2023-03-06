package ru.yandex.practicum.filmorate.utils;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.activity.Like;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeRowMapper implements RowMapper<Like> {

    @Override
    public Like mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Like(resultSet.getLong("user_id")
                        , resultSet.getLong("film_id"));
    }
}