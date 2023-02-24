package ru.yandex.practicum.filmorate.utils;

/**
 * Жанры фильмов
 */
public enum FilmGenre {

    N_A(),
    COMEDY(),
    DRAMA(),
    ANIMATION(),
    THRILLER(),
    DOCUMENTARY(),
    ACTION();

    private final String  displayText;

    FilmGenre() {
        String str = this.toString();
        displayText = str.toLowerCase().replace((char) (str.charAt(0) + 32), str.charAt(0));
    }

    public String display() {return displayText;}
}
