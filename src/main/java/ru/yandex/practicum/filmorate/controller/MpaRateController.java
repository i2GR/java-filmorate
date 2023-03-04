package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.FilmMpaRating;
import ru.yandex.practicum.filmorate.service.mpa.MpaRateServable;

import javax.validation.Valid;
import java.util.List;

/**
 * реализация функционала ендпойнтов для MPA-рейтинга
 * ТЗ-11
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/mpa")
public class MpaRateController {

    @NonNull
    private final MpaRateServable mpaRateService;

    /**
     * получение списка MPA-рейтингов
     *
     * @return список MPA-рейтингов (экземпляры FilmMpaRating)
     */
    @GetMapping
    public List<FilmMpaRating> getAll() {
        log.info("get all genres request");
        return mpaRateService.getAll();
    }

    /**
     * получение жанра по идентификатору
     *
     * @param id идентификатор (Integer)
     * @return жанр (экземпляр Genre)
     */
    @GetMapping("/{id}")
    public FilmMpaRating getById(@Valid @PathVariable Integer id) {
        log.info("get genre request with id {}", id);
        return mpaRateService.getMpaRateById(id);
    }

}
