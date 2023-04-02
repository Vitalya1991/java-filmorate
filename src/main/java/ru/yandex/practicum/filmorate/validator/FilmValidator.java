package ru.yandex.practicum.filmorate.validator;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;

@Component
public class FilmValidator {


    @SneakyThrows
    public static boolean validation(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("Bad film name");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Too mach description");
        }

        if (film.getDuration() <= 0) {
            throw new ValidationException("Duration must be a positive");
        }
        return true;
    }
}