package ru.yandex.practicum.filmorate.storage.activity.likes;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.activity.Like;
import ru.yandex.practicum.filmorate.storage.activity.InMemoryActivityStorage;

@Component
public class InMemoryLikesStorage extends InMemoryActivityStorage<Like> implements LikeStorable {

    public InMemoryLikesStorage() {
        super("Like");
    }
}
