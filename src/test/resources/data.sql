DELETE FROM genres;
DELETE FROM rating_MPA;
DELETE FROM friends;
DELETE FROM user_liked_film;
DELETE FROM film_genres;

INSERT INTO genres (id, name)
VALUES (1, 'Комедия'),
        (2, 'Драма'),
        (3, 'Мультфильм'),
        (4, 'Триллер'),
        (5, 'Документальный'),
        (6, 'Боевик');

INSERT INTO rating_MPA (id, mpa)
VALUES (1, 'G'),
        (2, 'PG'),
        (3, 'PG-13'),
        (4, 'R'),
        (5, 'NC-17');

INSERT INTO users (email, login, name, birthday)
VALUES ('mail1@mail.ru', 'login1', 'name1', PARSEDATETIME('2000-01-01', 'yyyy-MM-dd'));

INSERT INTO users (email, login, name, birthday)
VALUES ('second@mail.ru', 'second', 'name2', PARSEDATETIME('2001-01-01', 'yyyy-MM-dd'));

INSERT INTO films (title, description, release, duration)
VALUES ('nisi eiusmod', 'adipisicing', PARSEDATETIME('1967-03-25', 'yyyy-MM-dd'), 100);

INSERT INTO films (title, description, release, duration, rate, mpa_id)
VALUES ('New film', 'New film about friends', PARSEDATETIME('1999-04-30', 'yyyy-MM-dd'), 120, 4, 3);

INSERT INTO user_liked_film (user_id,film_id)
VALUES (1, 1),
       (2, 1);

INSERT INTO film_genres (film_id, genre_id)
VALUES (1, 1),
       (1, 3),
       (2, 5);

INSERT INTO friends (owner_id, friend_id)
VALUES (1, 2);

