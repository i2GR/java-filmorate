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
INNER JOIN films AS f ON f.mpa_id = r.id 
WHERE f.id = ?;
```

* рейтинга MPA по идентификатору (ФП-11.2)
```SQL
SELECT * FROM rating_MPA WHERE id = ?;
```
</details>

<details><summary><h3>Проверка:</h3></summary>

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
SELECT films.*, rating_MPA.mpa 
FROM user_liked_film AS likes 
RIGHT OUTER JOIN films ON likes.film_id = films.id
LEFT OUTER JOIN rating_MPA ON films.mpa_id = rating_MPA.id
GROUP BY films.id
ORDER BY COUNT(likes.user_id) DESC 
LIMIT ?;
-- количество фильмов передается в REST-запросе
```

* жанров фильма (по идентификатору)
```SQL
SELECT g.* 
FROM film_genres AS fg
INNER JOIN genres AS g ON fg.genre_id = g.id
WHERE fg.film_id = ?;
```

* друзей пользователя (по идентификатору пользователя)

```SQL
SELECT *
FROM users AS u 
INNER JOIN friends AS f ON f.friend_id = u.id 
WHERE f.owner_id = ?;
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