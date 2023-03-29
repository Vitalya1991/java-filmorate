package ru.yandex.practicum.filmorate.storage;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component("InMemoryFilmStorage")
public class InMemoryFilmStorage extends AbstractInMemoryStorage<Film> implements FilmStorage {
    private int id = 1;
    Map<Integer, Film> storage = new HashMap<>();
    @Override
    public Film add(Film film) {
        film.setId(id);
        storage.put(id, film);
        id++;
        return film;
    }

    @Override
    public Film replace(Film film) {
        storage.replace(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        storage.remove(film.getId());
        return film;
    }

    @Override
    public Film getById(int id) {
        return storage.get(id);
    }

    @Override
    public Collection<Film> getValues() {
        return storage.values()
                .stream()
                .sorted(this::compare)
                .collect(Collectors.toList());
    }


    private int compare(Film first, Film last) {
        return Integer.compare(first.getId(), last.getId());
    }
}
