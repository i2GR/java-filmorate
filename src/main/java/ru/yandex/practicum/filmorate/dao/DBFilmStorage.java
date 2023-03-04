package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.storage.entity.film.FilmStorage;
import ru.yandex.practicum.filmorate.utils.FilmRowMapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * реализация функционала репозитория базы данных пользователей
 * ТЗ-11
 */
@Primary
@Repository("filmDBStorage")
@RequiredArgsConstructor
public class DBFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Film> create(Film film) {
        String sqlQuery = "INSERT INTO films (title, description, release, duration, rate, mpa_id) "
                + "VALUES (?, ?, ?, ?, ?, ?); ";
        return insertFilmToDB(sqlQuery, film);
    }


    @Override
    public Optional<Film> readById(Long entityId) {
        String sqlQuery = "SELECT *  "
                + "FROM films AS f "
                + "INNER JOIN rating_MPA AS r ON f.mpa_id = r.id "
                + "WHERE f.id = ?;";
        return readFilmFromDB(sqlQuery, entityId);
    }

    @Override
    public Optional<Film> update(Film entity) {
        String sqlQuery = "UPDATE films SET "
                + "title = ?, description = ? , release = ?, duration = ?, rate = ?, mpa_id = ? "
                + "WHERE id = ?;";
        return updateFilmInDB(sqlQuery, entity);
    }

    @Override
    public List<Film> readAll() {
        String sqlQuery = "SELECT f.*, r.mpa FROM films AS f "
                + "INNER JOIN rating_MPA AS r ON f.mpa_id = r.id;";
        return readAllFilmsFromDB(sqlQuery);
    }


    @Override
    public boolean delete(Long id) {
        String sqlQuery = "DELETE FROM films WHERE id = ?;";
        return deleteUserFromDB(sqlQuery, id);
    }

    private Optional<Film> insertFilmToDB(String sqlQuery, Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                        PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
                        stmt.setString(1, film.getName());
                        stmt.setString(2, film.getDescription());
                        stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
                        stmt.setInt(4, film.getDuration());
                        stmt.setInt(5, film.getRate());
                        stmt.setInt(6, film.getMpa().getId());
                        return stmt;
                    }
                    , keyHolder);
            Long newFilmId = keyHolder.getKey().longValue();
            film.setId(newFilmId);
            return Optional.of(film);
        } catch (DataAccessException dae) {
            // empty: goto return;
        } catch (NullPointerException npe) {
            // empty: goto return;
        }
        return Optional.empty();
    }

    private Optional<Film> updateFilmInDB(String sqlQuery, Film film) {
        try {
            int affectedRows = jdbcTemplate.update(sqlQuery
                    , film.getName()
                    , film.getDescription()
                    , film.getReleaseDate()
                    , film.getDuration()
                    , film.getRate()
                    , film.getMpa().getId()
                    , film.getId());
            if (affectedRows == 1) {
                return Optional.of(film);
            }
            return Optional.empty();
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    private Optional<Film> readFilmFromDB(String sqlQuery, Long id) {
        try {
            Film film = jdbcTemplate.queryForObject(sqlQuery, new FilmRowMapper(), id);
            return Optional.ofNullable(film);
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    private List<Film> readAllFilmsFromDB(String sqlQuery) {
        List<Film> films = jdbcTemplate.query(sqlQuery, new FilmRowMapper());
        Map<Long, List<Genre>> filmIdToGenresList = getFilmGenresCommon();
        for (Film film : films) {
            film.setGenres(filmIdToGenresList.getOrDefault(film.getId(), new ArrayList<>()));
        }
        return films;
    }

    private boolean deleteUserFromDB(String sqlQuery, Long id) {
        try {
            return jdbcTemplate.update(sqlQuery, id) == 1;
        } catch (DataAccessException dae) {
            return false;
        }
    }

/*    private List<Genre> getGenresById(Long filmId) {
        String sqlQuery = "SELECT g.* "
                          + "FROM film_genres AS fg "
                          + "INNER JOIN genres AS g ON fg.genre_id = g.id "
                          + "WHERE fg.film_id = ?;";
        return jdbcTemplate.query(sqlQuery
                                , new GenreRowMapper()
                                , filmId);
    }*/

    private Map<Long, List<Genre>> getFilmGenresCommon() {
        String sqlQuery = "SELECT fg.film_id, g.* "
                + "FROM film_genres AS fg "
                + "LEFT OUTER JOIN genres AS g ON fg.genre_id = g.id;";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sqlQuery);
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
        return filmIdToGenresList;
    }

/*    private Map<Long, List<String>> getGenresCommon() {
        String sqlQuery = "SELECT fg.film_id AS film_id, g.* "
                + "FROM film_genres AS fg "
                + "LEFT OUTER JOIN genres AS g ON fg.genre_id = g.id;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery);
        Map<Long, List<String>> filmIdToGenresList = new HashMap<>();
        while(rowSet.next()) {
            Long mapKey = rowSet.getLong("film_id");
            filmIdToGenresList.get(mapKey).add(rowSet.getString("name"));
        }
        return filmIdToGenresList;
    }*/
}