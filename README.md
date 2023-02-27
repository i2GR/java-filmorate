## Промежуточное задание спринта 11 (11.1) -  Создание схемы БД

### Схема БД
https://app.quickdatabasediagrams.com/#/d/1rc7ry
![filmorate_db2.png](/filmorate_db2.png)

#### примечания к таблицам

<details><summary><h3>friends</h3></summary>
вспомогательная таблица связи "многие ко многим"
из реализации  спринта 10 пара <code>initiator - acceptor</code> должна быть уникальной  
и без учета того, какой ID является <code>initiator</code>ом или <code>acceptor</code>-ом  
</details>

<details><summary><h3>rating_MPA</h3></summary>
выделена отдельная таблица и выбрано использование суррогатного ключа, на (гипотетическую) возможность изменения кодов рейтинга   
</details>

### Примеры SQL запросов

<details><summary><h3>Добавление сущностей</h3></summary>

* добавление пользователя

```SQL
INSERT INTO users (email, login, name, birthday)  
VALUES ('newuser1@mail.ru'
        , 'login1'
        , 'name1'
        , CAST('yyyy-MM-dd' AS DATE)
) RETURNING *;
```

* добавление фильма

```SQL
INSERT INTO films (title, film_description, release, duration, rating_id)  
VALUES ('title'  
        , 'description'  
        , CAST('yyyy-MM-dd' AS DATE)  
        , [int]  
        , (SELECT rating FROM public."FilmRatingMPA" WHERE description = 'value')  
) RETURNING *;
```
* добавление лайка

```SQL
INSERT INTO user_like_to_film (user_id, film_id)  
VALUES ('[bigint]'  -- user_id
        , '[bigint]'  -- film_id  
) RETURNING *;
```
* добавление друзей
  (неподтвержденный статус - запрос со стороны инициатора)

```SQL
INSERT INTO friends (user_id_initiator, user_id_acceptor, status)  
VALUES ('[bigint]'  -- user_id_initiator
        , '[bigint]'  -- user_id_acceptor 
        , FALSE 
) RETURNING *;
```
</details>

<details><summary><h3>Получение сущностей по идентификатору</h3></summary>

* получение пользователя
```SQL
SELECT *  
FROM users 
WHERE user_id = ?; -- заданный идентификатор
```

* получение фильма
```SQL
SELECT *  
FROM films 
WHERE film_id = ?; -- заданный идентификатор
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
EXISTS(
SELECT *  
FROM user_like_to_film
WHERE film_id = ? -- заданный идентификатор
      AND user_id = ? -- заданный идентификатор
);
```
</details>

<details><summary><h3>Обновление сущностей</h3></summary>

* обновление пользователя

```SQL
UPDATE users
SET email = 'new email'
    , login = 'new login'
    , name = 'new name'
    , birthday = CAST('yyyy-MM-dd' AS DATE) -- обновленное значение для даты рождения
WHERE user_id = ? -- заданный идентификатор
RETURNING *;
```

* обновление фильма

```SQL
UPDATE films
SET title = 'new title'
    , film_description  = 'film_description'
    , release = CAST('yyyy-MM-dd' AS DATE) -- обновленное значение для даты релиза
    , duration = <value>
    , rating_id = <value>
WHERE film_id = ? -- заданный идентификатор
RETURNING *;
```

* подтверждение друзей
  (подтверждение со стороны акцептора)

```SQL
UPDATE friends 
SET status = TRUE
WHERE user_id_initiator = ? -- initiator id
      AND user_id_acceptor = ? -- acceptor_id 
RETURNING status;
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
SELECT *
FROM films;
```

* топ-N фильмов с информацией о количестве лайков (N: переданное значение количества фильмов)
```SQL
SELECT	f.* 
		, COUNT(l.user_id)
FROM user_like_to_film" AS l 
LEFT OUTER JOIN films AS f ON f.film_id = l.film_id
GROUP BY f.film_id  
ORDER BY COUNT(l.user_id) DESC
LIMIT <N>
```
* жанров фильма (по идентификатору)
```SQL
SELECT 
g.category
FROM film_genre AS fg
LEFT JOIN films AS f ON f.film_id = fg.film_id
LEFT JOIN genres AS g ON fg.genre_id = g.genre_id
WHERE f.film_id = <id> 
```
* друзей пользователя (по идентификатору пользователя)
```SQL
SELECT * AS  
FROM friends AS f1  
WHERE f1.user_id_initiator = ? -- заданный идентификатор 
UNION  
SELECT *  
FROM friends AS f2  
WHERE f2.user_id_acceptor = ? -- заданный идентификатор  
```
* общих друзей

```SQL
SELECT CASE
WHEN (initiator = <id1> AND acceptor != <id2>) THEN acceptor
WHEN (initiator != <id2> AND acceptor = <id1>) THEN initiator
END AS mutuals
FROM friends

INTERSECT

SELECT CASE
WHEN (initiator = <id2> AND acceptor != <id1>) THEN acceptor
WHEN (initiator != <id1> AND acceptor = <id2>) THEN initiator
END
FROM friends
```
</details>


