package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class DBUserStorageTest {

    private final DBUserStorage userStorage;

    private final JdbcTemplate jdbcTemplate;
    User user3, user4, user5;

    @BeforeEach
    void reinitialiseUsers() {
        jdbcTemplate.update("DELETE FROM friends WHERE owner_id > 2 OR friend_id > 2;");
        jdbcTemplate.update("DELETE FROM users WHERE id > 2");
    }


    @Test
    void create() {
        user3 = User.builder().name("user3").login("user3").email("test@mail.ru").birthday(LocalDate.EPOCH).build();
        Optional<User> userOptional = userStorage.create(user3);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                 assertThat(user).hasNoNullFieldsOrProperties());
    }

    @Test
    void readById() {
        Optional<User> optUser1 = userStorage.readById(1L);

        assertThat(optUser1)
                .isPresent()
                .hasValueSatisfying(user -> assertThat(user)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "name1")
                .hasFieldOrPropertyWithValue("login", "login1")
                .hasFieldOrPropertyWithValue("email", "mail1@mail.ru")
                .hasFieldOrPropertyWithValue("birthday", LocalDate.of(2000, 1, 1)));
    }

    @Test
    public void readByBadId() {
        assertTrue(userStorage.readById(-1L).isEmpty());
    }

    @Test
    void update() {
        user4 = User.builder().name("user4").login("user4").email("400@mail.ru").birthday(LocalDate.EPOCH).build();
        user4 = userStorage.create(user4).orElseThrow();
        user4.setName("user400");
        user4.setEmail("user4@mail.ru");
        Long id = user4.getId();

        Optional<User> updatedUser4Optional = userStorage.update(user4);

        assertThat(updatedUser4Optional)
                .isPresent()
                .hasValueSatisfying(user -> assertThat(user)
                        .hasFieldOrPropertyWithValue("id", id)
                        .hasFieldOrPropertyWithValue("name", "user400")
                        .hasFieldOrPropertyWithValue("login", "user4")
                        .hasFieldOrPropertyWithValue("email", "user4@mail.ru")
                        .hasFieldOrPropertyWithValue("birthday", LocalDate.EPOCH));
    }

    @Test
    void readAll() {
        List<User> users = userStorage.readAll();

        assertEquals( 2 , users.size());
        assertThat(users.get(0))
                    .hasFieldOrPropertyWithValue("id", 1L)
                    .hasFieldOrPropertyWithValue("name", "name1")
                    .hasFieldOrPropertyWithValue("login", "login1")
                    .hasFieldOrPropertyWithValue("email", "mail1@mail.ru")
                    .hasFieldOrPropertyWithValue("birthday", LocalDate.of(2000, 1, 1));
    }

    @Test
    void delete() {
        user5 = User.builder().name("fifth").login("fifth").email("555@mail.ru").birthday(LocalDate.EPOCH).build();
        Long userIdAdded = userStorage.create(user5).orElseThrow().getId();
        int exp = userStorage.readAll().size();
        userStorage.delete(userIdAdded);

        int act = userStorage.readAll().size();

        assertEquals(act + 1, exp);
    }
}