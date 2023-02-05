package ru.yandex.practicum.filmorate.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exception.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.entity.Entity;
import ru.yandex.practicum.filmorate.service.EntityServable;

import java.util.List;

/**
 * Контроллер с базовым функционалом:
 * ендпойнты добавления / обновления / получения по идентификатору / удаоения / получения списка
 * применяется для объектов, к которым применяются идентификатор (фильм, пользователь)
 * ТЗ-9
 * @param <T> подкласс Entity, для которого определен метод получения по идентификатору
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BasicController<T extends Entity> {

    /**
     * ссылка на слой сервиса
     */
    @Autowired
    @NonNull
    protected final EntityServable<T> service;

    protected static Logger log() {
        return log;
    }

    /**
     * добавление объекта
     * @return экземпляр добавленного объекта
     */
    public T addNew(T entity) throws ValidationException {
        return service.addNewEntity(entity);
    }

    /**
     * получение объекта Entity по id
     * @param id идентификатор объекта Entity
     * @return экземпляр класса, соответствующий переданному идентификатору
     */
    public T getById(Long id){
        return service.getEntity(id);
    }

    /**
     * обновление объекта
     * @return экземпляр добавленного объекта
     */
    public T update(T entity){
        return service.updateEntity(entity);
    }

    /**
     * получения объектов
     * @return список объектов
     */
    public List<T> getAll() {
        return service.getAll();
    }

    /**
     * метод перенаправления исключения валидации параметров передаваемых в ендпойнты
     * @param exception ValidationException
     * в текущей реализации в исключение передается стандартное сообщение исключения
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected void handleMethodArgumentNotValid (MethodArgumentNotValidException exception) {
        throw new ValidationException(exception.getMessage());
    }
}