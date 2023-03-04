package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorable;
import ru.yandex.practicum.filmorate.utils.GenreRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * реализация функционала репозитория жанров (БД)
 * ТЗ-11
 */
@Repository("genreDBStorage")
@RequiredArgsConstructor
public class DBGenreStorage implements GenreStorable {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> readAll() {
        String sqlQuery = "SELECT * FROM genres;";
        return jdbcTemplate.query(sqlQuery, new GenreRowMapper());
    }

    @Override
    public Optional<Genre> readGenreById(Integer id) {
        String sqlQuery = "SELECT * FROM genres WHERE id = ?;";
        try {
            Genre genre = jdbcTemplate.queryForObject(sqlQuery, new GenreRowMapper(), id);
            return Optional.ofNullable(genre);
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    public int updateGenresForFilm(Film film) throws DataAccessException {
        Long filmId = film.getId();
        List<Genre> list = film.getGenres();
        //int affectedRows = 0;
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id = ?;", filmId);
        //SqlRowSet tst = jdbcTemplate.queryForRowSet("SELECT COUNT(id) AS cnt FROM film_genres WHERE film_id = ?;", filmId);
        //tst.first();
       // int cnt = tst.getInt("cnt");
        String sqlQuery = " INSERT INTO film_genres (film_id, genre_id) "
                + "VALUES (?,?);";
        int[] batchedInputs = jdbcTemplate.batchUpdate(sqlQuery
                , new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, filmId);
                        ps.setLong(2, list.get(i).getId());
                    }

                    public int getBatchSize() {
                        return list.size();
                    }
                });
        return batchedInputs.length;
    }

    @Override
    public List<Genre> readByFilmId(Long filmId) {
        String sqlQuery = "SELECT * "
                + "FROM genres "
                + "WHERE id IN "
                + "(SELECT genre_id "
                + "FROM film_genres "
                + "WHERE film_id = ?)";
        return jdbcTemplate.query(sqlQuery, new GenreRowMapper(), filmId);
    }
}