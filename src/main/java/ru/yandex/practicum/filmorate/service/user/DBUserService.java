package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DBFriendStorage;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * реализация CRUD-функционала в сервис-слое для пользователей
 * хранение данных в БД
 * ТЗ-11
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class DBUserService implements UserServable {

    /**
     * хранилище в БД
     */
    @NonNull
    private final UserStorage userDBStorage;
    private final DBFriendStorage friendDBStorage;

    /**
     * создание пользователя в БД
     * реализация с присвоением имени пользователя в случае отсутствия присвоение имени по логину
     * @param user создаваемый пользователь (из HTTP-запроса)
     * @return созданный пользователь, эквивалентный записанному в базу
     */
    @Override
    public User create(User user) {
        user = renameOnLogin(user);
        Optional<User> optionalUser = userDBStorage.create(user);
        log.info("received data from DB {} when record:", optionalUser.isPresent());
        return optionalUser.orElseThrow();
    }

    @Override
    public User readById(@NonNull Long entityId){
        Optional<User> optionalUser = userDBStorage.readById(entityId);
        log.info("received data from DB {}", optionalUser.isPresent());
        return optionalUser.orElseThrow();
    }

    /**
     * обновление пользователя в БД
     * реализация с присвоением имени пользователя в случае отсутствия присвоение имени по логину
     */
    @Override
    public User update(User user) {
        user = renameOnLogin(user);
        Optional<User> optionalUser = userDBStorage.update(user);
        log.info("received data from DB {}", optionalUser.isPresent());
        return optionalUser.orElseThrow();
    }

    @Override
    public List<User> readAll() {
        List<User> userList = userDBStorage.readAll();
        log.info("received data from DB {}", userList.size());
        return userList;
    }

    @Override
    public List<User> getMutualFriends(Long userId1, Long userId2) {
        Set<User> friendsList1 = new HashSet<>(friendDBStorage.getAllFriends(userId1));
        log.info("received data from DB for user {}", userId1);
        Set<User> friendsList2 = new HashSet<>(friendDBStorage.getAllFriends(userId2));
        log.info("received data from DB for user {}", userId1);
        return friendsList1.stream().filter(friendsList2::contains).collect(Collectors.toList());
    }

    @Override
    public List<User> getAllFriends(Long id) {
        List<User> friendList = friendDBStorage.getAllFriends(id);
        log.info("received data from DB: {}", friendList.size());
        return friendList;
    }
}