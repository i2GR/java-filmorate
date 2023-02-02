package ru.yandex.practicum.filmorate.service.user;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.service.BasicEntityService;
import ru.yandex.practicum.filmorate.storage.activity.friends.FriendsStorable;
import ru.yandex.practicum.filmorate.storage.entity.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends BasicEntityService<User> implements UserServable{

    @NonNull
    @Autowired
    private final FriendsStorable friendPairsStorage;

    public UserService(@NonNull FriendsStorable FriendsStorable, @NonNull UserStorage userStorage) {
        super(userStorage);
        this.friendPairsStorage = FriendsStorable;
    }

    @Override
    public User addNewEntity(@NonNull User user) {
        return super.addNewEntity(renameOnLogin(user));
    }

    @Override
    public User updateEntity(@NonNull User user) {
        return super.updateEntity(renameOnLogin(user));
    }

    public FriendPair joinUpFriends(Long userId1, Long userId2) {
        return friendPairsStorage.create(new FriendPair(userId1, userId2));
    }

    public boolean areFriends(Long userId1, Long userId2) {
        return friendPairsStorage.read(new FriendPair(userId1, userId2));
    }

    public FriendPair breakFriends(Long userId1, Long userId2) {
        return friendPairsStorage.delete(new FriendPair(userId1, userId2));
    }

    public List<User> getMutualFriends(Long userId1, Long userId2) {
        List<User> commonFriends = new ArrayList<>();
        Set<Long> userId1Friends = friendPairsStorage.getActivitiesById(userId1);
        Set<Long> userId2Friends = friendPairsStorage.getActivitiesById(userId2);
        return userId1Friends.stream()
                .filter(id1 -> userId2Friends.contains(id1))
                .map(id1 -> getEntity(id1))
                .collect(Collectors.toList());
    }

    /**
     * метод для подстановки логина пользователя в качестве имени при незаполненном поле Name
     * @param user экз. User из запроса
     * @return экз. User с заполенным полем Name
     * @throws ValidationException исключение
     */
    private User renameOnLogin(User user) throws ValidationException {
        try {
            String userName = user.getName();
            if (userName == null || userName.isBlank()) {
                user.setName(user.getLogin());
                log.info("User.name received is empty. User renamed based on Login");
            }
        } catch (NullPointerException npe) {
            throw new ValidationException("Non correct User object received");
        }
        return user;
    }


}
