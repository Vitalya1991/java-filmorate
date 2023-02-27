package ru.yandex.practicum.filmorate.validator;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.ValidationAdvince;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Component
public class FilmValidator {

    @SneakyThrows
    public void validate(Film film) {
        if(LocalDate.of(1895, 12,28).isAfter(film.getReleaseDate())){
            throw new ValidationAdvince();
        }
    }
}