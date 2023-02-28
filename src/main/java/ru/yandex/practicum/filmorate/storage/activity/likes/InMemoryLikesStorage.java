package ru.yandex.practicum.filmorate.storage.activity.likes;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.StorageDuplicateException;
import ru.yandex.practicum.filmorate.exception.StorageNotFoundException;
import ru.yandex.practicum.filmorate.model.activity.Like;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * реализация слоя хранилища для лайков
 * ТЗ-10
 */
@Slf4j
@Component
@NoArgsConstructor
public class InMemoryLikesStorage implements LikeStorable {

    private final Set<Like> likeSet = new HashSet<>();

    public Like create(Like like) {
        log.debug("creating Like of user {} for film {}", like.getUserId(), like.getFilmId());
        if (likeSet.add(like)) { // события не было -> добавление
            log.debug("Like recorded in memory");
            return like;
        }
        throw new StorageDuplicateException("Like to add is already exists");
    }

    public boolean read(Like like) {
        log.debug("reading like of user {} for film {}", like.getUserId(), like.getFilmId());
        return likeSet.contains(like);
    }

    public Like delete(Like like) {
        log.debug("deleting like of user {} for film {}", like.getUserId(), like.getFilmId());
        if (likeSet.remove(like)) {
            log.debug("deleted");
            return like;
        }
        throw new StorageNotFoundException(String.format("Like of user %s to film %s is not present"
                                                        , like.getUserId().toString()
                                                        , like.getFilmId().toString()));
    }

    public Set<Long> getUsersForFilmById(Long filmId) {
        log.debug("get List of liked users by id {}", filmId);
        return likeSet.stream()
                .map(activity -> activity.getPairedId(filmId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}