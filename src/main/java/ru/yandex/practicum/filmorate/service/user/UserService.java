package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.service.UserServiceException;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.IdServable;
import ru.yandex.practicum.filmorate.service.IdService;
import ru.yandex.practicum.filmorate.service.friend.FriendServable;
import ru.yandex.practicum.filmorate.storage.activity.friends.FriendsStorable;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * реализация CRUD-функционала в сервис слое для пользователей
 * ТЗ-10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserServable, FriendServable {

    @NonNull
    private final FriendsStorable friendPairsStorage;

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
        return userStorage.create(user);
    }

    @Override
    public User readById(@NonNull Long entityId){
        return userStorage.readById(entityId);
    }

    /**
     * реализация с присвоением имени пользователя в случае отсутствия присвоение имени по логину
     */
    @Override
    public User update(User user) {
        user = renameOnLogin(user);
        return userStorage.update(user);
    }

    @Override
    public List<User> readAll() {
        return userStorage.readAll();
    }

    /**
     * реализация с проверкой наличия пользователей в хранилище
     * при отсутствии фильма или пользователя будет выброшено исключение
     */
    @Override
    public FriendPair joinUpFriends(@Valid Long userId1, @Valid Long userId2) {
        userStorage.readById(userId1);
        userStorage.readById(userId2);
        return friendPairsStorage.create(new FriendPair(userId1, userId2));
    }

    @Override
    public boolean areFriends(@Valid Long userId1, @Valid Long userId2) {
        return friendPairsStorage.read(new FriendPair(userId1, userId2));
    }

    @Override
    public FriendPair breakFriends(@Valid Long userId1, @Valid Long userId2) {
        return friendPairsStorage.delete(new FriendPair(userId1, userId2));
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
                .collect(Collectors.toList());
    }

    /**
     * метод для подстановки логина пользователя в качестве имени при незаполненном поле Name
     * @param user экз. User из запроса
     * @return экз. User с заполенным полем Name
     * @throws UserServiceException в случае любой ошибки в экземпляре User
     */
    private User renameOnLogin(User user) throws UserServiceException {
        try {
            String userName = user.getName();
            if (userName == null || userName.isBlank()) {
                user.setName(user.getLogin());
                log.info("User.name received is empty. User renamed based on Login");
            }
        } catch (RuntimeException t) {
            throw new UserServiceException("Non correct User object received:incorrect User login", user);
        }
        return user;
    }
}