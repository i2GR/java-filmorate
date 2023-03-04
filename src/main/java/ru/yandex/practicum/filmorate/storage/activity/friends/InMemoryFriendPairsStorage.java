package ru.yandex.practicum.filmorate.storage.activity.friends;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.StorageDuplicateException;
import ru.yandex.practicum.filmorate.exception.StorageNotFoundException;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * реализация слоя хранилища для статусов друзей
 * ТЗ-10
 */
@Slf4j
@Component
@NoArgsConstructor
public class InMemoryFriendPairsStorage implements FriendsStorable {

    private final Set<FriendPair> friendPairSet = new HashSet<>();

    public Optional<FriendPair> create(Long initiatorId, Long friendId) {
        log.debug("creating friends status");
        FriendPair friendPair = new FriendPair(initiatorId, friendId);
        if (friendPairSet.add(friendPair)) { // события не было -> добавление
            log.debug("recorded friends status of userIds {} and {}"
                                            , friendPair.getFriendIdOne()
                                            , friendPair.getFriendIdTwo());
            return Optional.of(friendPair);
        }
        throw new StorageDuplicateException(String.format("users %s and %s are already friends"
                                            , friendPair.getFriendIdOne().toString()
                                            , friendPair.getFriendIdTwo().toString()));
    }

    @Override
    public Optional<FriendPair> delete(Long ownerId, Long friendId) {
        log.debug("deleting friends status of {} and {}"
                                            , ownerId
                                            , friendId);
        FriendPair friendPair = new FriendPair(ownerId, friendId);
        if (friendPairSet.remove(friendPair)) {
            log.debug("deleted");
            return Optional.of(friendPair);
        }
        throw new StorageNotFoundException(String.format("users %s and %s are not present"
                                            , friendPair.getFriendIdOne().toString()
                                            , friendPair.getFriendIdTwo().toString()));
    }

    /**
     * получение списка всех друзей пользователя по переданному идентификатору
     * @param id идентификатор искомого пользователя
     * @return Сет идентификаторов пользователей-друзей
     */
    public Set<Long> getFriendsById(Long id) {
        log.debug("get List of friends by id {}", id);
        return friendPairSet.stream()
                .map(activity -> activity.getPairedId(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}