package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.model.entity.Film;
import ru.yandex.practicum.filmorate.storage.activity.likes.LikeStorable;
import ru.yandex.practicum.filmorate.utils.FilmRowMapper;
import ru.yandex.practicum.filmorate.utils.LikeRowMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * ТЗ-10<p>
 * Интерфейс для хранилища лайков фильмов от пользователей
 */
@Slf4j
@Primary
@Repository("likeDBStorage")
@RequiredArgsConstructor
public class DBLikeStorage implements LikeStorable {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Like> create(Like like) {
        String sqlQuery = "INSERT INTO user_liked_film (user_id, film_id) "
                + "VALUES (?, ?);";
        log.info("SQL request fo create like from user {} to Film {}", like.getUserId(), like.getFilmId());
        return updateLikeInDB(sqlQuery, like);
    }

    public boolean read(Like like) {
        String sqlQuery = "SELECT * FROM user_liked_film WHERE user_id = ? AND film_id = ?;";
        log.info("SQL request fo read like from user {} to Film {}", like.getUserId(), like.getFilmId());
        return readLikeFromDB(sqlQuery, like).isPresent();
    }

    public Optional<Like> delete(Like like) {
        String sqlQuery = "DELETE FROM user_liked_film WHERE user_id = ? AND film_id = ?;";
        log.info("SQL request fo delete like from user {} to Film {}", like.getUserId(), like.getFilmId());
        return updateLikeInDB(sqlQuery, like);
    }

    public Set<Long> getUsersForFilmById(Long filmId) {
        String sqlQuery = "SELECT user_id "
                + "FROM  user_liked_film "
                + "WHERE film_id = ?;";
        log.info("SQL request to get likes of Film {}", filmId);
        return new HashSet<>(
                jdbcTemplate.query(sqlQuery
                        , (rs, rowNum) -> rs.getLong("user_id")
                        , filmId)
        );
    }

    public List<Film> getTopFilms(Integer count) {
        String sqlQuery = "SELECT films.*, rating_MPA.mpa "
                        + "FROM user_liked_film AS likes "
                        + "RIGHT OUTER JOIN films ON likes.film_id = films.id "
                        + "LEFT OUTER JOIN rating_MPA ON films.mpa_id = rating_MPA.id "
                        + "GROUP BY films.id "
                        + "ORDER BY COUNT(likes.user_id) DESC "
                        + "LIMIT ?;";
        log.info("SQL request for top {} films", count);
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper(), count);
    }

    private Optional<Like> updateLikeInDB(String sqlQuery, Like like) {
        try {
            int affectedRows = jdbcTemplate.update(sqlQuery, like.getUserId(), like.getFilmId());
            if (affectedRows == 1) {
                log.info("OK updating like");
                return Optional.of(like);
            }
            log.error("Error updating like. {} records affected", affectedRows);
            return Optional.empty();
        } catch (DataAccessException dae) {
            log.error("Error updating like. DataAccessException");
            return Optional.empty();
        }
    }

    private Optional<Like> readLikeFromDB(String sqlQuery, Like like) {
        try {
            Like likeInDB = jdbcTemplate.queryForObject(sqlQuery, new LikeRowMapper(), like.getUserId(), like.getFilmId());
            log.info("OK reading reading like from user {} to Film {}", like.getUserId(), like.getFilmId());
            return Optional.ofNullable(likeInDB);
        } catch (DataAccessException dae) {
            log.error("Error reading like. DataAccessException");
            return Optional.empty();
        }
    }
}