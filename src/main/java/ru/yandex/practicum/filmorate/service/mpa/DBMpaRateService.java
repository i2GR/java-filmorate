package ru.yandex.practicum.filmorate.service.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FilmMpaRating;
import ru.yandex.practicum.filmorate.storage.mpa.MpaRateStorable;

import java.util.List;
import java.util.Optional;

/**
 * реализация CRUD-функционала в сервис слое для рейтингов MPA
 * ТЗ-11
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class DBMpaRateService implements MpaRateServable {

    /**
     * хранилище в БД
     */
    @NonNull
    private final MpaRateStorable mpaRateStorage;

    @Override
    public List<FilmMpaRating> getAll() {
        List<FilmMpaRating> mpaRateList = mpaRateStorage.readAll();
        log.info("received data from DB {}",  mpaRateList.size());
        return  mpaRateList;
    }

    @Override
    public FilmMpaRating getMpaRateById(@NonNull Integer id){
        Optional<FilmMpaRating> optionalMpaRate = mpaRateStorage.readMpaRateById(id);
        log.info("received data from DB {}", optionalMpaRate.isPresent());
        return optionalMpaRate.orElseThrow();
    }
}