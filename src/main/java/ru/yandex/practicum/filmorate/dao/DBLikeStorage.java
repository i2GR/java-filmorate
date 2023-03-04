package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
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
@Primary
@Repository("likeDBStorage")
@RequiredArgsConstructor
public class DBLikeStorage implements LikeStorable {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Like> create(Like like) {
        String sqlQuery = "INSERT INTO user_liked_film (user_id, film_id) "
                + "VALUES (?, ?);";
        return updateLikeInDB(sqlQuery, like);
    }

    public boolean read(Like like) {
        String sqlQuery = "SELECT * FROM user_liked_film WHERE user_id = ? AND film_id = ?;";
        return readLikeFromDB(sqlQuery, like).isPresent();
    }

    public Optional<Like> delete(Like like) {
        String sqlQuery = "DELETE FROM user_liked_film WHERE user_id = ? AND film_id = ?;";
        return updateLikeInDB(sqlQuery, like);
    }

    public Set<Long> getUsersForFilmById(Long filmId) {
        String sqlQuery = "SELECT user_id "
                + "FROM  user_liked_film "
                + "WHERE film_id = ?;";
        return new HashSet<>(
                jdbcTemplate.query(sqlQuery
                        , (rs, rowNum) -> rs.getLong("user_id")
                        , filmId)
        );
    }

    public List<Film> getTopFilms(Integer count) {
        String sqlQuery = "SELECT * "
                + "FROM films "
                + "LEFT OUTER JOIN "
                + "(SELECT film_id, COUNT(user_id) AS like_count "
                + "FROM user_liked_film "
                + "GROUP BY film_id) AS likes "
                + "ON likes.film_id = films.id "
                + "LEFT OUTER JOIN rating_MPA ON films.mpa_id = rating_MPA.id "
                + "ORDER BY likes.like_count DESC "
                + "LIMIT ?;";
        return jdbcTemplate.query(sqlQuery, new FilmRowMapper(), count);
    }

    private Optional<Like> updateLikeInDB(String sqlQuery, Like like) {
        try {
            int affectedRows = jdbcTemplate.update(sqlQuery, like.getUserId(), like.getFilmId());
            if (affectedRows == 1) {
                return Optional.of(like);
            }
            return Optional.empty();
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

    private Optional<Like> readLikeFromDB(String sqlQuery, Like like) {
        try {
            Like likeInDB = jdbcTemplate.queryForObject(sqlQuery, new LikeRowMapper(), like.getUserId(), like.getFilmId());
            return Optional.ofNullable(likeInDB);
        } catch (DataAccessException dae) {
            return Optional.empty();
        }
    }

}