package ru.yandex.practicum.filmorate.storage.activity.friends;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.storage.activity.InMemoryActivityStorage;


@Component
public class InMemoryFriendPairsStorage extends InMemoryActivityStorage<FriendPair> implements FriendsStorable {

    public InMemoryFriendPairsStorage() {
        super("Friends");
    }
}
