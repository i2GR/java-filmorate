package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import ru.yandex.practicum.filmorate.model.entity.Entity;
import ru.yandex.practicum.filmorate.storage.entity.EntityStorable;

import java.util.List;

/**
 * Сервис с базовым функционалом:
 *  добавление / обновление / получение по идентификатору / удаоения / получения списка
 *  (реализация ТЗ-9)
 *  применяется для объектов, к которым применяются идентификатор (фильм, пользователь)
 * @implNote двойное наследование/имплементация(вопрос комментария)
 * Аналогичный вопрос по BasicController
 * @param <T>подкласс Entity, для которого определен метод получения по идентификатору
 */
/*
Вопрос: должен ли этот абстрактный класс имплементировать EntityServable
с учетом того, что InMemory...Service наследуются от BasicEntityService и применяет ...Service по ТЗ
...Service наследуется от EntityServable
для класса Сервиса получается и наследование от абстрактного класса и применение дженерик-интерфеса через наследника
Аналогичный вопрсо есть и понаследованию контроллеров
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BasicEntityService<T extends Entity> implements EntityServable<T> {

    private Long id = 1L;

    @lombok.NonNull
    private final String entityType;

    @Autowired
    protected final EntityStorable<T> storage;

    protected Long setIdByService() {
        return id++;
    }

    public T addNewEntity(T entity) {
        entity = getEntityWithCheckedId(entity);
        return storage.create(entity);
    }

    public T getEntity(@NonNull Long entityId){
        return storage.read(entityId);
    }

    public T updateEntity(T entity) {
        return storage.update(entity);
    }

    public T deleteEntity(@NonNull Long entityId) {
        return storage.delete(entityId);
    }

    public List<T> getAll() {
        return storage.getAll();
    }

    protected T getEntityWithCheckedId(T entity) {
        if (entity.getId() == null) {
            entity.setId(setIdByService());
            log.debug("assigned new ID {} to {}", id, entityType);
            return entity;
        }
        log.debug("ID {} received by Entity {} used", id, entityType);
        return entity;
    }
}
