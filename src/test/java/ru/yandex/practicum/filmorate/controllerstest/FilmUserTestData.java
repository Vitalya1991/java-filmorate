package ru.yandex.practicum.filmorate.controllerstest;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

public class FilmUserTestData {

    public static Film film = new Film(1, "New film", "Some description", LocalDate.of(2020, 10, 13),120);

    public static User user = new User(1, "lol@mail.ru", "login", "name", LocalDate.of(1980, 5, 13));
}