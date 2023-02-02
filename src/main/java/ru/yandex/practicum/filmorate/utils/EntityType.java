package ru.yandex.practicum.filmorate.utils;

public enum EntityType {
    FILM(),
    USER();

    private final String value;
    EntityType() {
        String str = this.toString();
        value = str.toLowerCase().replace((char) (str.charAt(0) + 32), str.charAt(0));
    }
    public String val() {return value;}

}
