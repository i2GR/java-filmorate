package ru.yandex.practicum.filmorate.utils;

/**
 * возрастное ограничение для фильма
 * @implNote рейтинг Ассоциации кинокомпаний (MPA)
 */
public enum FilmRating {

    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String  displayRating;

    FilmRating(String str) {
        displayRating = str;
    }

    public String display() {
        return displayRating;
    }
}
