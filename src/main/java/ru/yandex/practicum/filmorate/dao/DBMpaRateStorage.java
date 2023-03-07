package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmMpaRating;
import ru.yandex.practicum.filmorate.storage.mpa.MpaRateStorable;
import ru.yandex.practicum.filmorate.utils.MpaRateRowMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class DBMpaRateStorage implements MpaRateStorable {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<FilmMpaRating> readAll() {
        String sqlQuery = "SELECT * FROM rating_MPA;";
        log.info("SQL request to read stored mpa-ratings list");
        return jdbcTemplate.query(sqlQuery, new MpaRateRowMapper());
    }

    @Override
    public Optional<FilmMpaRating> readMpaRateById(Integer id) {
        String sqlQuery = "SELECT * FROM rating_MPA WHERE id = ?;";
        log.info("SQL request to read  mpa-ratings for film id {}", id);
        try {
            FilmMpaRating mpaRate = jdbcTemplate.queryForObject(sqlQuery, new MpaRateRowMapper(), id);
            log.info("OK reading mpa-ratings for film");
            return Optional.ofNullable(mpaRate);
        } catch (DataAccessException dae) {
            log.error("Error reading mpa-ratings for film. DataAccessException");
            return Optional.empty();
        }
    }

    @Override
    public int updateMpaRateFilmById(Long filmId, FilmMpaRating mpaRate) {
        String sqlQuery = "UPDATE films SET "
                + "mpa_id = ? "
                + "WHERE id = ?;";
        log.info("SQL request to update mpa-rating for film Id {}", filmId);
        return jdbcTemplate.update(sqlQuery, mpaRate.getId(), filmId);
    }

    @Override
    public Optional<FilmMpaRating> readByFilmId(Long filmId) {
        String sqlQuery = "SELECT r.* "
                + "FROM rating_MPA AS r "
                + "INNER JOIN films AS f ON f.mpa_id = r.id "
                + "WHERE f.id = ?;";
        log.info("SQL request to read mpa-rating for film Id {}", filmId);
        try {
            FilmMpaRating rateMpa = jdbcTemplate.queryForObject(sqlQuery, new MpaRateRowMapper(), filmId);
            log.info("OK reading mpa-rating.");
            return Optional.ofNullable(rateMpa);
        } catch (DataAccessException dae) {
            log.error("Error reading mpa-ratings for film. DataAccessException");
            return Optional.empty();
        }
    }
}
