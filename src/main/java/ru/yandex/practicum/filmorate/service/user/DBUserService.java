package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;

import java.util.List;
import java.util.Optional;

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
    @Qualifier("userDBStorage")
    private final UserStorage userStorage;

    /**
     * создание пользователя в БД
     * реализация с присвоением имени пользователя в случае отсутствия присвоение имени по логину
     * @param user создаваемый пользователь (из HTTP-запроса)
     * @return созданный пользователь, эквивалентный записанному в базу
     */
    @Override
    public User create(User user) {
        user = renameOnLogin(user);
        Optional<User> optionalUser = userStorage.create(user);
        log.info("received data from DB {} when record:", optionalUser.isPresent());
        return optionalUser.orElseThrow();
    }

    @Override
    public User readById(@NonNull Long entityId){
        Optional<User> optionalUser = userStorage.readById(entityId);
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
        Optional<User> optionalUser = userStorage.update(user);
        log.info("received data from DB {}", optionalUser.isPresent());
        return optionalUser.orElseThrow();
    }

    @Override
    public List<User> readAll() {
        List<User> userList = userStorage.readAll();
        log.info("received data from DB {}", userList.size());
        return userList;
    }
}