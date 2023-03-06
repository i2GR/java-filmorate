package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.FilmMpaRating;

import java.util.List;
import java.util.Optional;

/**
 * ТЗ-11<p>
 * Интерфейс для хранилища рейтингов MPA
 */
public interface MpaRateStorable {

    /**
     * получение списка всех рейтингов MPA
     * @return список рейтингов MPA (экземпляры FilmMpaRating)
     */
    List<FilmMpaRating> readAll();


    /**
     * получение рейтинга MPA для фильма из базы по идентификатору
     * @param id идентификатор фильма
     * @return рейтинг MPA
     */
    Optional<FilmMpaRating> readMpaRateById(Integer id);

    /**
     * обновление рейтинга MPA для фильма из базы по идентификатору
     * @param filmId идентификатор фильма
     * @param mpaRate записываемый рейтинг MPA для фильма
     * @return количество измененных строк в БД (таблица films)
     */
    int updateMpaRateFilmById(Long filmId, FilmMpaRating mpaRate);

    /**
     * получение  рейтинга MPA фильма из базы по идентификатору фильма
     * @param filmId идентификатор фильма
     * @return рейтинг MPA
     */
    Optional<FilmMpaRating> readByFilmId(Long filmId);
}
