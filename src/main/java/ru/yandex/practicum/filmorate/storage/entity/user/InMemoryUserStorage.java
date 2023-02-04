package ru.yandex.practicum.filmorate.storage.entity.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.entity.User;
import ru.yandex.practicum.filmorate.storage.entity.InMemoryEntityStorage;
import ru.yandex.practicum.filmorate.utils.EntityType;

@Component
public class InMemoryUserStorage extends InMemoryEntityStorage<User> implements UserStorage {

    public InMemoryUserStorage() {
        super(EntityType.USER.val());
    }
}
