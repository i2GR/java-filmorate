package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmMpaRating;
import ru.yandex.practicum.filmorate.storage.mpa.MpaRateStorable;
import ru.yandex.practicum.filmorate.utils.MpaRateRowMapper;

import java.util.List;
import java.util.Optional;

@Repository("mpaDBStorage")
@RequiredArgsConstructor
public class DBMpaRateStorage implements MpaRateStorable {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<FilmMpaRating> readAll() {
        String sqlQuery = "SELECT * FROM rating_MPA;";
        return jdbcTemplate.query(sqlQuery, new MpaRateRowMapper());
    }

    @Override
    public Optional<FilmMpaRating> readMpaRateById(Integer id) {
        String sqlQuery = "SELECT * FROM rating_MPA WHERE id = ?;";
        try {
            FilmMpaRating mpaRate = jdbcTemplate.queryForObject(sqlQuery, new MpaRateRowMapper(), id);
            return Optional.ofNullable(mpaRate);
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    @Override
    public int updateMpaRateFilmById(Long filmId, FilmMpaRating mpaRate) {
        String sqlQuery = "UPDATE films SET "
                + "mpa_id = ? "
                + "WHERE id = ?;";
        return jdbcTemplate.update(sqlQuery, mpaRate.getId(), filmId);
    }

    @Override
    public Optional<FilmMpaRating> readByFilmId(Long filmId) {
        String sqlQuery = "SELECT r.* "
                + "FROM rating_MPA AS r "
                + "WHERE id IN "
                + "(SELECT f.mpa_id FROM films AS f WHERE f.id = ?);";
        try {
            FilmMpaRating rateMpa = jdbcTemplate.queryForObject(sqlQuery, new MpaRateRowMapper(), filmId);
            return Optional.ofNullable(rateMpa);
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }
}
