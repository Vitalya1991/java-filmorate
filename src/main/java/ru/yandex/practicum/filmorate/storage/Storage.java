package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;

public interface Storage<T> {

    T add(T obj);

    T replace(T obj);

    T delete(T obj);

    T getById(int id);

    Collection<T> getValues();
}