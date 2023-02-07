package ru.yandex.practicum.filmorate.model.activity;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * класс хранения/передачи информации о статусе друзей
 * хранит  информацию о двух id пользователей являющихся друзьями
 * ТЗ-10
 */
public class FriendPair extends ActivityEvent {
    @Getter
    @NotNull(message = "user1 id is null")
    private final Long friendIdOne;
    @Getter
    @NotNull(message = "user2 id is null")
    private final Long friendIdTwo;

    public FriendPair(Long idOne, Long idTwo) {
        friendIdOne = idOne;
        friendIdTwo = idTwo;
    }

    /**
     * получение идентификатора "друга" по идентификатору пользователя
     * идентификатор "друга" может быть в любого одного поля,
     * если другое поле содержит идентификатор запрошенного пользователя
     *
     * @param userId идентификатор пользователя, для которого проводится "поиск" id друга
     * @return Optional со значением идентификатора друга, если запрошенный пользователь содержится в экземпляре класса
     * или Optional c null
     */
    @Override
    public Optional<Long> getPairedId(Long userId) {
        if (userId.longValue() == friendIdOne.longValue()) {
            return Optional.of(friendIdTwo);
        }
        if (userId.longValue() == friendIdTwo.longValue()) {
            return Optional.of(friendIdOne);
        }
        return Optional.empty();
    }

    /**
     * переопределение метода equals() реализовано так, чтобы объект считался равным для любых пар идентификаторов
     * т.е FriendPair(1,2) был равен FriendPair(2,1)
     * @param o экземпляр FriendPair для сравнения
     * @return true если данный экземпляр FriendPair равен переданному для сравнения, иначе false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FriendPair that = (FriendPair) o;
        return (friendIdOne.equals(that.friendIdOne) && friendIdTwo.equals(that.friendIdTwo))
                || (friendIdOne.equals(that.friendIdTwo) && friendIdTwo.equals(that.friendIdOne));
    }

    /**
     * переопределение метода hashCode() реализовано так, чтобы hash был одинаковым для любых пар идентификаторов
     * @implNote !!! Возможно - это тонкое место
     * @return хеш-код, определенный по сумме хеш-кодов полей.
     */
    @Override
    public int hashCode() {
        return friendIdOne.hashCode() + friendIdTwo.hashCode();
    }
}