package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.ValidationAdvince;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.database.interfaces.FilmDStorage;
import ru.yandex.practicum.filmorate.storage.database.interfaces.GenreStorage;
import ru.yandex.practicum.filmorate.storage.database.interfaces.LikeStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmService(@Qualifier("FilmDbStorage") FilmDStorage filmStorage,
                       @Qualifier("UserDbStorage") UserStorage userStorage,
                       @Qualifier("GenreDbStorage") GenreStorage genreStorage,
                       @Qualifier("LikeDbStorage") LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.genreStorage = genreStorage;
        this.likeStorage = likeStorage;
    }

    public Film create(Film film) {
        validate(film);
        filmStorage.add(film);
        likeStorage.saveLikes(film);
        genreStorage.saveGenre(film);
        log.info("Фильм добавлен в коллекцию");
        return getById(film.getId());
    }

    public Film update(Film film) {
        validate(film);
        if (filmStorage.storage.containsKey(film.getId())) {
            filmStorage.replace(film);
            likeStorage.saveLikes(film);
            genreStorage.saveGenre(film);
            return getById(film.getId());
        }
        log.error("Фильм в коллекции не найден");
        throw new FilmNotFoundException("Ошибка при обновлении: фильм id = " + film.getId() + " не найден");
    }

    public Collection<Film> findAll() {
        Collection<Film> films = filmStorage.getValues();
        films.forEach(this::loadData);
        return films;
    }

    public Film getById(Integer filmId) {
        if (!filmStorage.storage.containsKey(filmId)) {
            log.error("Фильм в коллекции не найден");
            throw new FilmNotFoundException("Ошибка при поиске: фильм id = " + filmId + " не найден");
        }
        Film film = filmStorage.getById(filmId);
        loadData(film);
        return film;
    }


    public Film addUserLike(int filmId, int userId) {
        if (!filmStorage.storage.containsKey(filmId)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при добавлении лайка: пользователь c id = " + userId + " не найден");
        }
        if (!getUsersIds().contains(userId)) {
            log.error("Фильм в коллекции не найден");
            throw new FilmNotFoundException("Ошибка при добавлении лайка: фильм c id = " + filmId + " не найден");
        }
        Film film = filmStorage.getById(filmId);
        film.addUserLike(userId);
        likeStorage.saveLikes(film);
        return film;
    }


    public Film deleteUserLike(int filmId, int userId) {
        if (!filmStorage.storage.containsKey(filmId)) {
            log.error("Фильм в коллекции не найден");
            throw new FilmNotFoundException("Ошибка при удалении лайка: фильм c id = " + filmId + " не найден");
        }
        if (!userStorage.storage.containsKey(userId)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при удалении лайка: пользователь c id = " + userId + " не найден");
        }
        Film film = filmStorage.getById(filmId);
        film.deleteUserLike(userId);
        likeStorage.saveLikes(film);
        return film;
    }

    public Collection<Film> returnPopularFilms(@DefaultValue("10") Integer size) {
        Collection<Film> films = filmStorage.getValues();
        films.forEach(this::loadData);
        return films.stream()
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

    private int compare(Film first, Film last) {
        return Integer.compare(last.getUsersLikes().size(), first.getUsersLikes().size());
    }

    private void loadData(Film film) {
        likeStorage.loadLikes(film);
        genreStorage.loadGenre(film);
    }
}