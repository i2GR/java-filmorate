package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
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
@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class DBFilmStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Film> create(Film film) {
        String sqlQuery = "INSERT INTO films (title, description, release, duration, rate, mpa_id) "
                + "VALUES (?, ?, ?, ?, ?, ?); ";
        log.info("SQL request for Film create id");
        return insertFilmToDB(sqlQuery, film);
    }

    @Override
    public Optional<Film> readById(Long filmId) {
        String sqlQuery = "SELECT *  "
                + "FROM films AS f "
                + "INNER JOIN rating_MPA AS r ON f.mpa_id = r.id "
                + "WHERE f.id = ?;";
        log.info("SQL request for Film read id {}", filmId);
        return readFilmFromDB(sqlQuery, filmId);
    }

    @Override
    public Optional<Film> update(Film film) {
        String sqlQuery = "UPDATE films SET "
                + "title = ?, description = ? , release = ?, duration = ?, rate = ?, mpa_id = ? "
                + "WHERE id = ?;";
        log.info("SQL request for Film update id {}", film.getId());
        return updateFilmInDB(sqlQuery, film);
    }

    @Override
    public List<Film> readAll() {
        String sqlQuery = "SELECT f.*, r.mpa FROM films AS f "
                + "INNER JOIN rating_MPA AS r ON f.mpa_id = r.id;";
        log.info("SQL request for All-Film List");
        return readAllFilmsFromDB(sqlQuery);
    }

    @Override
    public boolean delete(Long id) {
        String sqlQuery = "DELETE FROM films WHERE id = ?;";
        log.info("SQL request for Film delete with id {}", id);
        return deleteUserFromDB(sqlQuery, id);
    }

    private Optional<Film> insertFilmToDB(String sqlQuery, Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            int affectedRow = jdbcTemplate.update(connection -> {
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
            log.info("DB Film create ({} record made)", affectedRow);
            Number n = keyHolder.getKey();
            if (n != null) {
                Long newFilmId = n.longValue();
                film.setId(newFilmId);
                log.info("OK inserting Film to DB with new id {}", newFilmId);
                return Optional.of(film);
            }
            return Optional.empty();
        } catch (DataAccessException dae) {
            log.error("DB DataAccessException creating Film");
            return Optional.empty();
        }
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
                log.info("OK Film updating in DB");
                return Optional.of(film);
            }
            log.error("Error updating Data in DB: not One record affected");
            return Optional.empty();
        } catch (DataAccessException dae) {
            log.error("DB DataAccessException updating Film");
            return Optional.empty();
        }
    }

    private Optional<Film> readFilmFromDB(String sqlQuery, Long id) {
        try {
            Film film = jdbcTemplate.queryForObject(sqlQuery, new FilmRowMapper(), id);
            log.info("OK film reading from DB");
            return Optional.ofNullable(film);
        } catch (DataAccessException dae) {
            log.info("DB DataAccessException reading Film");
            return Optional.empty();
        }
    }

    private List<Film> readAllFilmsFromDB(String sqlQuery) {
        List<Film> films = jdbcTemplate.query(sqlQuery, new FilmRowMapper());
        log.info("OK reading Film-list. {} records found", films.size());
        return films;
    }

    private boolean deleteUserFromDB(String sqlQuery, Long id) {
        try {
            int affectedRows = jdbcTemplate.update(sqlQuery, id);
            if (affectedRows == 1) {
                log.info("OK deleting film for DB.{} film deleted, id {}", affectedRows, id);
                return true;
            }
            log.error("Error: Not one but{} DB record affected by Request", affectedRows);
        } catch (DataAccessException dae) {
            log.info("DB DataAccessException deleting Film with id {}", id);
        }
        return false;
    }
}