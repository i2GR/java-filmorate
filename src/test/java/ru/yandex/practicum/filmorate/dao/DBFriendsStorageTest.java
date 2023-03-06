package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.activity.FriendPair;
import ru.yandex.practicum.filmorate.model.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DBFriendsStorageTest {

    private final DBFriendsStorage friendStorage;
    private final DBUserStorage userStorage;
    private final JdbcTemplate jdbcTemplate;
    private final User user3 = User.builder().name("user3").login("user3").email("test@mail.ru").birthday(LocalDate.EPOCH).build();

    @BeforeEach
    void reinitialiseFriends() {
        jdbcTemplate.update("DELETE FROM friends WHERE owner_id > 2 OR friend_id > 2;");
        jdbcTemplate.update("DELETE FROM users WHERE id > 2;");
    }

    @Test
    void create() {
        Long id3 = userStorage.create(user3).orElseThrow().getId();
        Optional<FriendPair> addedFriends = friendStorage.create(2L, id3);

        assertThat(addedFriends)
                .isPresent()
                .hasValueSatisfying(like ->
                        assertThat(like).hasNoNullFieldsOrProperties()
                                .hasFieldOrPropertyWithValue("friendIdOne", 2L)
                                .hasFieldOrPropertyWithValue("friendIdTwo", id3));
    }

    @Test
    void delete() {
        long[] initialFriendsOf2 = friendStorage.getAllFriends(2L)
                .stream().map(User::getId)
                .mapToLong(L -> L)
                .toArray();
        Long id3 = userStorage.create(user3).orElseThrow().getId();
        Optional<FriendPair> addedFriends = friendStorage.create(2L, id3);
        long[] afterAddFriendsOf2 = friendStorage.getAllFriends(2L)
                .stream().map(User::getId)
                .mapToLong(L -> L)
                .toArray();
        Optional<FriendPair> deletedFriends = friendStorage.delete(2L, id3);
        long[] afterDeleteFriendsOf2 = friendStorage.getAllFriends(2L)
                .stream().map(User::getId)
                .mapToLong(L -> L)
                .toArray();

        assertEquals(0, initialFriendsOf2.length);
        assertEquals(1, afterAddFriendsOf2.length);
        assertArrayEquals(new long[]{id3}, afterAddFriendsOf2);
        assertEquals(0, afterDeleteFriendsOf2.length);
    }

    @Test
    void getAllFriends() {
        Long id3 = userStorage.create(user3).orElseThrow().getId();
        User user2db = userStorage.readById(2L).orElseThrow();
        User user3db = userStorage.readById(id3).orElseThrow();
        friendStorage.create(1L, id3);
        List<User> friends = friendStorage.getAllFriends(1L);

        assertEquals(2, friends.size());
        assertEquals(user2db, friends.get(0));
        assertEquals(user3db, friends.get(1));
    }
}