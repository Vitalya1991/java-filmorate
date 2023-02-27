package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Getter
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    final FilmValidator filmValidator;
    private int id = 1;

    public FilmController(FilmValidator filmValidator) {
        this.filmValidator = filmValidator;
    }

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Получен запрос на получение списка фильмов");
        return films.values();
    }


    @SneakyThrows
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        filmValidator.validate(film);
        if (film.getDuration() <= 0){throw new ValidationAdvince();
        }
        film.setId(id);
        id++;
        films.put(film.getId(), film);
        log.info("Вы - {}!", " обновили данные для нового фильма");
        return film;
    }

    @SneakyThrows
    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film){
        filmValidator.validate(film);
        if (!films.containsKey(film.getId())){throw new ValidationAdvince();
        }
        log.info("Вы - {}!", " обновили данные для текущего фильма");
        films.put(film.getId(), film);
        return film;
    }

}




