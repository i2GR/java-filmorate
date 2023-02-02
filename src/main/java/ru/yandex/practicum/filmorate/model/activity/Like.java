package ru.yandex.practicum.filmorate.model.activity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RequiredArgsConstructor
@EqualsAndHashCode
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
     * получение идентификатора фильма по идентификатору пользователя
     * или получение идентификатора пользователя по идентификатору фильма
     * возможно тонкое место, требущее переработки
     *
     * @param id идентификатор сущности, для которого проводится "поиск" id парной сущности
     * @return Optional со значением идентификатора, если запрошенная сущность содержится в экземпляре класса
     * или Optional c null
     */
    //TODO
    // переработать?
    @Override
    public Optional<Long> getPairedId(Long id) {
        if (userId.longValue() == id.longValue()) return Optional.of(filmId);
        if (filmId.longValue() == id.longValue()) return Optional.of(userId);
        return Optional.empty();
    }
}
