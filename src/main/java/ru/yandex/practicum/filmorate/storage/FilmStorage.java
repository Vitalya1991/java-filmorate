package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

public interface FilmStorage extends Storage<Film> {

     Map<Integer, Film> films = new HashMap<>();
}