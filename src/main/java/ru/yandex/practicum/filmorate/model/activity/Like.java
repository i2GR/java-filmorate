package ru.yandex.practicum.filmorate.model.activity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * класс лайка.
 * хранит информацию какой пользователь поставил лайк и какому фильму
 * информация о пользователе/фильме посредством идентификаторов
 * ТЗ-10
 */
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Like extends ActivityEvent {

    /**
     * идентификатор пользователя, поставившего лайк
     */
    @Getter
    @NotNull(message = "userId is null")
    private final Long userId;

    /**
     * идентификатор фильма, которому поставил лайк пользователь
     */
    @Getter
    @NotNull(message = "filmId is null")
    private final Long filmId;

    /**
     * получение идентификатора пользователя по идентификатору фильма
     * @param id идентификатор сущности, для которого проводится "поиск" id парной сущности пользователь-фильм
     * @return Optional со значением идентификатора, если запрошенная сущность содержится в экземпляре класса
     * или Optional c null
     */
    @Override
    public Optional<Long> getPairedId(Long id) {
        if (filmId.longValue() == id.longValue()) return Optional.of(userId);
        return Optional.empty();
    }
}
