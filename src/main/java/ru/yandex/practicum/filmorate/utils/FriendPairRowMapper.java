package ru.yandex.practicum.filmorate.utils;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendPairRowMapper implements RowMapper<FriendPair> {

    @Override
    public FriendPair mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new FriendPair(resultSet.getLong("owner_id")
                              , resultSet.getLong("friend_id"));
    }
}