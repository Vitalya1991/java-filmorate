package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.ValidationAdvince;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film create(Film film) {
        validate(film);
        filmStorage.add(film);
        log.info("Фильм добавлен в коллекцию");
        return film;
    }

    public Film update(Film film) {
        validate(film);
        if (getIds().contains(film.getId())) {
            filmStorage.replace(film);
            log.info("Фильм обновлен в коллекции");
        } else {
            log.error("Фильм в коллекции не найден");
            throw new FilmNotFoundException("Ошибка при обновлении: фильм id = " + film.getId() + " не найден");
        }
        return film;
    }

    public Collection<Film> findAll() {
        log.info("Возвращен список фильмов");
        return filmStorage.getValues();
    }

    public Film getById(Integer filmId) {
        if (!FilmStorage.films.containsKey(filmId)) {
            log.error("Фильм в коллекции не найден");
            throw new FilmNotFoundException("Ошибка при поиске: фильм id = " + filmId + " не найден");
        }
        return filmStorage.getById(filmId);
    }


    public Film addUserLike(int filmId, int userId) {
        if (!FilmStorage.films.containsKey(filmId)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при добавлении лайка: пользователь c id = " + userId + " не найден");
        }
        if (!getUsersIds().contains(userId)) {
            log.error("Фильм в коллекции не найден");
            throw new FilmNotFoundException("Ошибка при добавлении лайка: фильм c id = " + filmId + " не найден");
        }
        filmStorage.getById(filmId).addUserLike(userId);
        return filmStorage.getById(filmId);
    }


    public Film deleteUserLike(int filmId, int userId) {
        if (!FilmStorage.films.containsKey(filmId)) {
            log.error("Фильм в коллекции не найден");
            throw new FilmNotFoundException("Ошибка при удалении лайка: фильм c id = " + filmId + " не найден");
        }
        if (!UserStorage.users.containsKey(userId)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при удалении лайка: пользователь c id = " + userId + " не найден");
        }
        filmStorage.getById(filmId).deleteUserLike(userId);
        return filmStorage.getById(filmId);
    }

    public Collection<Film> returnPopularFilms(@DefaultValue("10") Integer size) {
        return filmStorage.getValues().stream()
                .sorted(this::compare)
                .limit(size)
                .collect(Collectors.toSet());
    }

    @SneakyThrows
    public void validate(@Valid Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Ошибка валидации даты релиза фильма");
            throw new ValidationAdvince();
        }
    }

    private Collection<Integer> getIds() {
        return filmStorage.getValues().stream()
                .map(Film::getId)
                .collect(Collectors.toList());
    }

    private Collection<Integer> getUsersIds() {
        return userStorage.getValues().stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    private int compare(Film f0, Film f1) {
        return -1 * Integer.compare(f0.getUsersLikes().size(), f1.getUsersLikes().size());
    }
}