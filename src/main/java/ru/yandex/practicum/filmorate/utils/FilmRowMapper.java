package ru.yandex.practicum.filmorate.utils;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.FilmMpaRating;
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
                /* далее строится рейтинг вроде не по ORM ,т.к. рейтинг фильма формируется из одного запроса в БД -
                 но вроде на вебинаре озвучивалась мысль, что нужно сокращать количество обращений в БД
                 Получение для фильма и жанров тоже в один запров я уже не смог так осилить,
                 и кажется, что это не KISS-ово
                 */
                .mpa(FilmMpaRating.builder()
                                  .id(resultSet.getInt("mpa_id"))
                                  .name(resultSet.getString("mpa")).build())
                .build();
    }
}
