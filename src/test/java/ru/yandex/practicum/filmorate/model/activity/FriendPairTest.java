package ru.yandex.practicum.filmorate.model.activity;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FriendPairTest {

    private FriendPair friendPair1;
    private FriendPair friendPair2;
    private Long pair1ID1, pair1ID2, pair2ID1, pair2ID2;

    @Test
    void pairsWithNotEqualFields() {
        pair1ID1 = 1L;
        pair1ID2 = 2L;
        pair2ID1 = 3L;
        pair2ID2 = 4L;
        setupPairs();

        assertNotEquals(friendPair1, friendPair2);
        assertNotEquals(friendPair1.hashCode(), friendPair2.hashCode());
    }

    @Test
    void pairsWithFieldsOneEquals() {
        pair2ID1 = pair1ID1 = 1L;
        pair1ID2 = 2L;
        pair2ID2 = 4L;
        setupPairs();

        assertNotEquals(friendPair1, friendPair2);
        assertNotEquals(friendPair1.hashCode(), friendPair2.hashCode());
    }

    @Test
    void pairsWithFieldsTwoEquals() {
        pair1ID1 = pair2ID1 = 3L;
        pair1ID2 = 2L;
        pair2ID2 = 4L;
        setupPairs();

        assertNotEquals(friendPair1, friendPair2);
        assertNotEquals(friendPair1.hashCode(), friendPair2.hashCode());
    }

    @Test
    void pairsWithCorrespondingFieldsEquals() {
        pair1ID1 = pair2ID1 = 1L;
        pair1ID2 = pair2ID2 = 4L;
        setupPairs();

        assertEquals(friendPair1, friendPair2);
        assertEquals(friendPair1.hashCode(), friendPair2.hashCode());
    }

    @Test
    void pairsWithCrossedFieldsEquals() {
        pair1ID1 = pair2ID2 = 1L;
        pair1ID2 = pair2ID1 = 4L;
        setupPairs();

        assertEquals(friendPair1, friendPair2);
        assertEquals(friendPair1.hashCode(), friendPair2.hashCode());
    }

    @Test
    void pairsWithRandomCrossedFieldsEquals() {
        for (int i = 0; i < 10000; i++) {
            pair1ID1 = pair2ID2 = (new Random()).nextLong();
            pair1ID2 = pair2ID1 = (new Random()).nextLong();
            setupPairs();

            assertEquals(friendPair1, friendPair2);
            assertEquals(friendPair1.hashCode(), friendPair2.hashCode(), "Not passed random test at i = " + i);
        }
    }

    @Test
    void getLinkedUserId() {
        pair1ID1 = 1L;
        pair1ID2 = 2L;
        pair2ID1 = 3L;
        pair2ID2 = 4L;
        setupPairs();

        Optional<Long> result1 = friendPair1.getPairedId(1L);
        Optional<Long> result2 = friendPair1.getPairedId(2L);
        Optional<Long> result3 = friendPair2.getPairedId(3L);
        Optional<Long> result4 = friendPair2.getPairedId(4L);
        Optional<Long> result5 = friendPair1.getPairedId(0L);

        assertEquals(result1.get(), pair1ID2);
        assertEquals(result2.get(), pair1ID1);
        assertEquals(result3.get(), pair2ID2);
        assertEquals(result4.get(), pair2ID1);
        assertTrue(result5.isEmpty());

    }

    private void setupPairs() {
        friendPair1 = new FriendPair(pair1ID1, pair1ID2);
        friendPair2 = new FriendPair(pair2ID1, pair2ID2);
    }
}