package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.IdServable;
import ru.yandex.practicum.filmorate.service.IdService;
import ru.yandex.practicum.filmorate.service.friend.FriendServable;
import ru.yandex.practicum.filmorate.storage.activity.friends.InMemoryFriendPairsStorage;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * реализация CRUD-функционала в сервис слое для пользователей
 * ТЗ-10
 */
@Slf4j
@RequiredArgsConstructor
public class InMemoryUserService implements UserServable, FriendServable {

    @NonNull
    @Autowired
    private final InMemoryFriendPairsStorage friendPairsStorage;

    /**
     * хранилище пользователей
     */
    @NonNull
    protected final UserStorage userStorage;

    /**
     * сервис-слой обновлению идентификатора
     */
    private final IdServable<User> idService = new IdService<>(0L);

    /**
     * реализация с проверкой наличия идентификатора
     * реализация с присвоениемимени пользователя в случае отсутствия присвоение имени по логину
     */
    @Override
    public User create(User user) {
        user = idService.getEntityWithCheckedId(user);
        user = renameOnLogin(user);
        Optional<User> optionalUser = userStorage.create(user);
        log.info("received data from InMemory {}", optionalUser.isPresent());
        return optionalUser.orElseThrow();
    }

    @Override
    public User readById(@NonNull Long entityId){
        Optional<User> optionalUser = userStorage.readById(entityId);
        log.info("received data from InMemory {}", optionalUser.isPresent());
        return optionalUser.orElseThrow();
    }

    /**
     * реализация с присвоением имени пользователя в случае отсутствия присвоение имени по логину
     */
    @Override
    public User update(User user) {
        user = renameOnLogin(user);
        Optional<User> optionalUser = userStorage.update(user);
        log.info("received data from InMemory {}", optionalUser.isPresent());
        return optionalUser.orElseThrow();
    }

    @Override
    public List<User> readAll() {
        List<User> userList= userStorage.readAll();
        log.info("received data from InMemory {}", !userList.isEmpty());
        return userList;
    }

    /**
     * реализация с проверкой наличия пользователей в хранилище
     * при отсутствии фильма или пользователя будет выброшено исключение
     */
    @Override
    public FriendPair joinUpFriends(@Valid Long initiatorId, @Valid Long friendId) {
        userStorage.readById(initiatorId);
        userStorage.readById(friendId);
        Optional<FriendPair> friendPairOptional = friendPairsStorage.create(initiatorId, friendId);
        log.info("received data from InMemory {}", friendPairOptional.isPresent());
        return friendPairOptional.orElseThrow();
    }

    @Override
    public FriendPair breakFriends(@Valid Long ownerId, @Valid Long friendId) {
        Optional<FriendPair> friendPairOptional = friendPairsStorage.delete(ownerId, friendId);
        log.info("received data from InMemory {}", friendPairOptional.isPresent());
        return friendPairOptional.orElseThrow();
    }

    @Override
    public List<User> getMutualFriends(@Valid Long userId1, @Valid Long userId2) {
        Set<Long> userId1Friends = friendPairsStorage.getFriendsById(userId1);
        Set<Long> userId2Friends = friendPairsStorage.getFriendsById(userId2);
        return userId1Friends.stream()
                .filter(userId2Friends::contains)
                .map(this::readById)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllFriends(@Valid Long id) {
        return friendPairsStorage.getFriendsById(id)
                .stream()
                .map(userStorage::readById)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}