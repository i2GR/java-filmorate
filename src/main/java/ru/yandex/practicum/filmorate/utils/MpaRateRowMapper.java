package ru.yandex.practicum.filmorate.utils;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.FilmMpaRating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MpaRateRowMapper implements RowMapper<FilmMpaRating> {

    @Override
    public FilmMpaRating mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return FilmMpaRating.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("mpa"))
                    .build();
    }
}