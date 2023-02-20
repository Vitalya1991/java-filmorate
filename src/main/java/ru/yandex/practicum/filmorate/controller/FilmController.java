package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Getter
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    FilmValidator filmValidator;

    @GetMapping("/films")
    public Collection<Film> findAll() {
        log.info("Получен запрос на получение списка фильмов");
        return films.values();
    }


    @PostMapping ("/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {

        filmValidator.validate(film);
        films.put(film.getId(), film);
        log.info("Вы - {}!", " обновили данные для нового фильма");
        return film;
    }

    @PutMapping("/films")
    public Film putFilm(@Valid @RequestBody Film film) throws ValidationException {

        filmValidator.validate(film);
        film.setId(films.size() + 1);
        log.info("Вы - {}!", " обновили данные для текущего фильма");
        films.put(film.getId(), film);
        return film;
    }

}




