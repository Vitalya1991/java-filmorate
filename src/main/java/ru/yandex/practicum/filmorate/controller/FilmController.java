package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Getter
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public Collection<Film> findAll() {
        log.info("Получен запрос на получение списка фильмов");
        return films.values();
    }

    private void validateFilm(@Valid Film film) throws ValidationException {
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Длинна описания не может превышать 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быль раньше декабря 1895 года!");
        }
        if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
    }

    @PatchMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException {
        if(films.containsKey(film.getId())){
            log.error("Добавлен существющий фильм");
            throw new ValidationException("Фильм с id" + film.getId() + " уже зарегестрирова");
        }
        validateFilm(film);
        film.setId(films.size() + 1);
        films.put(film.getId(), film);
        log.info("Вы - {}!", " обновили данные для нового фильма");
        return film;
    }
    @PutMapping("/films")
    public Film putFilm(@Valid @RequestBody Film film) throws ValidationException{
        if(!films.containsKey(film.getId())){
            log.error("Такого фильма нет");
            throw new ValidationException("Такого фильма нет");
        }
        validateFilm(film);

        film.setId(films.size() + 1);
        log.info("Вы - {}!", " обновили данные для текущего фильма");
        films.put(film.getId(), film);
        return film;
    }

}




