package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.service.UserServiceException;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.BasicEntityService;
import ru.yandex.practicum.filmorate.service.friend.FriendServable;
import ru.yandex.practicum.filmorate.storage.activity.friends.FriendsStorable;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.EntityType;

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
public class UserService extends BasicEntityService<User> implements UserServable, FriendServable {

    @Autowired
    private final FriendsStorable friendPairsStorage;

    public UserService(@NonNull FriendsStorable FriendsStorable, @NonNull UserStorage userStorage) {
        super(EntityType.USER.val(), userStorage);
        this.friendPairsStorage = FriendsStorable;
    }

    /**
     * перегружен для работы проверки имени пользователя
     *  в случае отсутствия присвоение имени по логину
     */
    @Override
    public User addNewEntity(User user) {
        return super.addNewEntity(renameOnLogin(user));
    }

    /**
     * перегружен для работы проверки имени пользователя
     *  в случае отсутствия присвоение имени по логину
     */
    @Override
    public User updateEntity(User user) {
        return super.updateEntity(renameOnLogin(user));
    }

    /**
     * перегружен для работы проверки наличия пользователей в хранилище
     *  в случае отсутитвия присвоение имени по логину
     */
    @Override
    public FriendPair joinUpFriends(@Valid Long userId1, @Valid Long userId2) {
        storage.read(userId1);
        storage.read(userId2);
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
        Set<Long> userId1Friends = friendPairsStorage.getActivitiesById(userId1);
        Set<Long> userId2Friends = friendPairsStorage.getActivitiesById(userId2);
        return userId1Friends.stream()
                .filter(userId2Friends::contains)
                .map(this::getEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllFriends(@Valid Long id) {
        return friendPairsStorage.getActivitiesById(id)
                .stream()
                .map(storage::read)
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
