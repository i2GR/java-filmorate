package ru.yandex.practicum.filmorate.storage.activity.friends;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.storage.activity.InMemoryActivityStorage;

/**
 * реализация слоя хранилища CRUD-функционала для статусов друзей
 * ТЗ-10
 */
@Component
public class InMemoryFriendPairsStorage extends InMemoryActivityStorage<FriendPair> implements FriendsStorable {

    public InMemoryFriendPairsStorage() {
        super("Friends");
    }
}
