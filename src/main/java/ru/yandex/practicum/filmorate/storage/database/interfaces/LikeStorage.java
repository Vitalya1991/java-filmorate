package ru.yandex.practicum.filmorate.storage.database.interfaces;

import ru.yandex.practicum.filmorate.model.Film;

public interface LikeStorage  {

    void saveLikes(Film film);

    void loadLikes(Film film);
}
