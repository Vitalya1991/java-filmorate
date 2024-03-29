package ru.yandex.practicum.filmorate.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int id = 1;

    @Override
    public Film add(Film film) {
        film.setId(id);
        film.setUsersLikes(new HashSet<>());
        films.put(id, film);
        id++;
        return film;
    }

    @Override
    public Film replace(Film film) {
        film.setUsersLikes(new HashSet<>());
        films.replace(film.getId(), film);
       return film;
    }

    @Override
    public Film delete(Film film) {
        films.remove(film.getId());
        return film;
    }

    @Override
    public Film getById(int id) {
        return films.get(id);
    }

    @Override
    public Collection<Film> getValues() {
        return films.values()
                .stream()
                .sorted(this::compare)
                .collect(Collectors.toList());
    }


    private int compare(Film first, Film last) {
        return Integer.compare(first.getId(), last.getId());
    }
}
