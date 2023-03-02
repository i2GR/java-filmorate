package ru.yandex.practicum.filmorate.utils;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.FilmMPARating;
import ru.yandex.practicum.filmorate.model.entity.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmRowMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .rate(resultSet.getInt("rate"))
                .mpa(FilmMPARating.builder()
                                  .id(resultSet.getInt("mpa_id"))
                                  .name(resultSet.getString("mpa")).build())
                .build();
    }
}
