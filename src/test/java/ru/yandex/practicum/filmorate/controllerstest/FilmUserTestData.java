package ru.yandex.practicum.filmorate.controllerstest;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;

public class FilmUserTestData {

    public static Film film;

    static {
        try {
            film = new Film(1, "New film", "Some description", LocalDate.of(2020, 10, 13), Duration.ofMinutes(120));
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public static User user = new User(1, "lol@mail.ru", "login", LocalDate.of(1980, 5, 13), "name");
}