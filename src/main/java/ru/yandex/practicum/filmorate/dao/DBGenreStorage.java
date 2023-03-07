package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorable;
import ru.yandex.practicum.filmorate.utils.GenreRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * реализация функционала репозитория жанров (БД)
 * ТЗ-11
 */

@Slf4j
@Repository
@RequiredArgsConstructor
public class DBGenreStorage implements GenreStorable {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> readAll() {
        String sqlQuery = "SELECT * FROM genres;";
        log.info("SQL request to read all genres list");
        return jdbcTemplate.query(sqlQuery, new GenreRowMapper());
    }

    @Override
    public Optional<Genre> readGenreById(Integer id) {
        String sqlQuery = "SELECT * FROM genres WHERE id = ?;";
        log.info("SQL request to read genre by id {}", id);
        try {
            Genre genre = jdbcTemplate.queryForObject(sqlQuery, new GenreRowMapper(), id);
            log.info("OK reading genre by id {}", id);
            return Optional.ofNullable(genre);
        } catch (DataAccessException dae) {
            log.error("DB DataAccessException reading genre by Id {}", id);
            return Optional.empty();
        }
    }

    public int updateGenresForFilm(Film film) throws DataAccessException {
        Long filmId = film.getId();
        List<Integer> genresId = film.getGenres()
                                    .stream()
                                    .map(Genre::getId)
                                    .distinct()
                                    .collect(Collectors.toList());
        int affectedRows = jdbcTemplate.update("DELETE FROM film_genres WHERE film_id = ?;", filmId);
        log.info("deleted {} records in genres-table for film id {}", affectedRows, filmId);
        String sqlQuery = " INSERT INTO film_genres (film_id, genre_id) "
                         + "VALUES (?,?);";
        int[] batchedInputs = jdbcTemplate.batchUpdate(sqlQuery
                , new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, filmId);
                        ps.setLong(2, genresId.get(i));
                    }
                    public int getBatchSize() {
                        return genresId.size();
                    }
                });
        log.info("updated {} records in genres-table for film id {}", batchedInputs.length, filmId);
        return batchedInputs.length;
    }

    @Override
    public List<Genre> readByFilmId(Long filmId) {
        String sqlQuery = "SELECT g.* "
                + "FROM film_genres AS fg "
                + "INNER JOIN genres AS g ON fg.genre_id = g.id "
                + "WHERE fg.film_id = ?;";
        log.info("SQL request fo read genres for Film with id: {}", filmId);
        return jdbcTemplate.query(sqlQuery, new GenreRowMapper(), filmId);
    }

    @Override
    public Map<Long, List<Genre>> getFilmGenresCommon() {
        log.info("SQL request for getting genres list for films");
        String sqlQuery = "SELECT fg.film_id, g.* "
                + "FROM film_genres AS fg "
                + "LEFT OUTER JOIN genres AS g ON fg.genre_id = g.id;";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlQuery);
        log.info("SQL data received");
        Map<Long, List<Genre>> filmIdToGenresList = new HashMap<>();
        while (rows.next()) {
            Long key = rows.getLong("film_id");
            List<Genre> value = filmIdToGenresList.getOrDefault(key, new ArrayList<>());
            value.add(Genre.builder()
                    .id(rows.getInt("id"))
                    .name(rows.getString("name"))
                    .build());
            filmIdToGenresList.put(key, value);
        }
        log.info("mapped {} genres lists for films", filmIdToGenresList.size());
        return filmIdToGenresList;
    }
}