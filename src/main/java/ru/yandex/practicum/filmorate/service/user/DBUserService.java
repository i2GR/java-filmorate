package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.service.UserServiceException;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.IdServable;
import ru.yandex.practicum.filmorate.service.IdService;
import ru.yandex.practicum.filmorate.service.friend.FriendServable;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * реализация CRUD-функционала в сервис-слое для пользователей
 * хранение данных в БД
 * ТЗ-11
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class DBUserService implements UserServable, FriendServable {

    /**
     * хранилище в БД
     */
    @NonNull
    @Qualifier("userDBStorage")
    protected final UserStorage userStorage;

    /**
     * сервис-слой обновлению идентификатора
     */
    private final IdServable<User> idService = new IdService<>(0L);

    /**
     * реализация с присвоениемимени пользователя в случае отсутствия присвоение имени по логину
     */
    @Override
    public User create(User user) {
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
        //TODO
        return new FriendPair(0L,1L);//friendPairsStorage.create(new FriendPair(userId1, userId2));
    }

    @Override
    public boolean areFriends(@Valid Long userId1, @Valid Long userId2) {
        return true; //friendPairsStorage.read(new FriendPair(userId1, userId2));
    }

    @Override
    public FriendPair breakFriends(@Valid Long userId1, @Valid Long userId2) {
        //TODO
        return new FriendPair(0L,1L);//friendPairsStorage.delete(new FriendPair(userId1, userId2));
    }

    @Override
    public List<User> getMutualFriends(@Valid Long userId1, @Valid Long userId2) {
        //TODO
        /*Set<Long> userId1Friends = friendPairsStorage.getFriendsById(userId1);
        Set<Long> userId2Friends = friendPairsStorage.getFriendsById(userId2);
        return userId1Friends.stream()
                .filter(userId2Friends::contains)
                .map(this::readById)
                .collect(Collectors.toList());*/
        return new ArrayList<>();
    }

    @Override
    public List<User> getAllFriends(@Valid Long id) {
        //TODO
        /*return friendPairsStorage.getFriendsById(id)
                .stream()
                .map(userStorage::readById)
                .collect(Collectors.toList());*/
        return new ArrayList<>();
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