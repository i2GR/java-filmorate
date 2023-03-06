## ФП СП-11 (11.1, 11.2)

### Схема БД

https://app.quickdatabasediagrams.com/#/d/1rc7ry
![filmorate_db3.png](/filmorate_db3.png)

<details><summary><h3>примечания к таблицам</h3></summary>
ФП-11 предполагает "одностороннюю" дружбу, поэтому для каждого пользователя должен быть уникальный список друзей
(начальная ER-схема промедуточного задания 11.1 с только уникальными строками не выполняет такую функцию.

SQL-запросы скорректированы по результатаи тестов и новыми данными ФП-11

- исправлены ошибки, некоторая корректировка (оптимизация)
- реализация для **H2database** без режимов совместимости с **PostgreSQL**

</details>



### Примеры SQL запросов

<details><summary><h3>Добавление сущностей</h3></summary>

* добавление пользователя
```SQL
INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?);

-- передаваемые атрибуты (поля DTO класса User)
```

* добавление фильма
```SQL
INSERT INTO films (title, description, release, duration, rate, mpa_id)
VALUES (?, ?, ?, ?, ?, ?);

-- передаваемые атрибуты (поля DTO класса Film)
```

* добавление лайка
```SQL
INSERT INTO user_liked_film (user_id, film_id)
VALUES (?, ?);
```

* добавление друзей
```SQL
INSERT INTO friends (owner_id, friend_id) VALUES (?, ?);  -- передаваемые атрибуты (поля DTO класса User)
```
</details>

<details><summary><h3>Получение сущностей по идентификатору</h3></summary>

* получение пользователя
```SQL
SELECT * FROM users WHERE id = ?; -- заданный идентификатор
```

* получение фильма
```SQL
SELECT *
FROM films AS f
INNER JOIN rating_MPA AS r ON f.mpa_id = r.id
WHERE f.id = ?; -- заданный идентификатор
```

* рейтинга MPA по идентификатору фильма
```SQL
SELECT r.*
FROM rating_MPA AS r
WHERE id IN (SELECT f.mpa_id FROM films AS f WHERE f.id = ?);
```

* рейтинга MPA по идентификатору (ФП-11.2)
```SQL
SELECT * FROM rating_MPA WHERE id = ?;
```
</details>

<details><summary><h3>Проверка:</h3></summary>

* статуса дружбы (по идентификаторам пользователя-инициатора и пользователя-акцептора)
```SQL
SELECT status  
FROM friends 
WHERE user_initiator_id = ? -- заданный идентификатор
      AND user_acceptor_id = ?; -- заданный идентификатор
```

* лайка (по идентификаторам пользователя и фильма)
```SQL
SELECT * FROM user_liked_film WHERE user_id = ? AND film_id = ?;
```
</details>

<details><summary><h3>Обновление сущностей</h3></summary>

* обновление пользователя
```SQL
UPDATE users
SET email = ?
    , login = ?
    , name = ?
    , birthday = CAST('yyyy-MM-dd' AS DATE) -- обновленное значение для даты рождения
WHERE user_id = ?; -- заданный идентификатор
```

* обновление фильма

```SQL
UPDATE films SET 
title = ?, description = ? , release = ?, duration = ?, rate = ?, mpa_id = ? 
WHERE id = ?;
-- параметры передаются в REST-запросе
-- список жанров обновляется отдельно
```

* обновление списка жанров для фильма (часть обработки обновления фильма)

```SQL
DELETE FROM film_genres WHERE film_id = ?; -- очистка старых данных по идентификатору фильма
INSERT INTO film_genres (film_id, genre_id)
VALUES (?,?);
```

</details>

<details><summary><h3>Получение списков</h3></summary>

* всех пользователей

```SQL
SELECT *
FROM users;
```

* всех фильмов

```SQL
"SELECT f.*, r.mpa FROM films AS f
INNER JOIN rating_MPA AS r ON f.mpa_id = r.id;
-- список жанров обновляется отдельно
```

* топ-N фильмов с информацией о количестве лайков (N: переданное значение количества фильмов)

```SQL
SELECT * 
FROM films 
LEFT OUTER JOIN (SELECT film_id, COUNT(user_id) AS like_count
                FROM user_liked_film
                GROUP BY film_id) AS likes
                ON likes.film_id = films.id
                LEFT OUTER JOIN rating_MPA ON films.mpa_id = rating_MPA.id
                ORDER BY likes.like_count DESC
                LIMIT ?; -- количество фильмов передается в REST-запросе
```

* жанров фильма (по идентификатору)
```SQL
SELECT * 
FROM genres 
WHERE id IN (SELECT genre_id 
            FROM film_genres 
            WHERE film_id = ?);
```

* друзей пользователя (по идентификатору пользователя)

```SQL
SELECT *
FROM users
WHERE id IN (SELECT friend_id 
            FROM friends
            WHERE owner_id = ?);
```

* общих друзей
```SQL
SELECT * FROM users WHERE id IN (SELECT friend_id AS mutual 
                                FROM friends 
                                WHERE owner_id = ?    -- для пользователя **1**
                                INTERSECT 
                                SELECT friend_id 
                                FROM friends 
                                WHERE owner_id = ?);  -- для пользователя **2**
```

* жанров в ДБ (ФП-11.2)
```SQL
SELECT * FROM genres;
```

* рейтингов MPA в ДБ (ФП-11.2)
```SQL
SELECT * FROM rating_MPA;
```
</details>

<details><summary><h3>Удаление данных</h3></summary>

* пользователя (по идентификатору)
```SQL
DELETE FROM users WHERE id = ?; -- переданный идентификатор
```

* фильма (по идентификатору)
```SQL
DELETE FROM films WHERE id = ?; -- переданный идентификатор
```

* удаление из друзей (односторонее)
```SQL
DELETE FROM friends WHERE owner_id = ? AND  friend_id = ?;
```

* удаление лайка
```SQL
DELETE
FROM user_liked_film
WHERE user_id = ? AND film_id = ?; -- идентификаторы пользователя и фильма из REST-запроса
```
</details>