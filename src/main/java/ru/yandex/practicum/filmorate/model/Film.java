package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    @NotBlank(message = "Название фильма не должно быть пустым.")
    String name; // название
    int id; // целочисленный идентификатор
    @Size(max = 200, message = "Максимальная длина описания 200 символов")
    String description; // описание
    @Setter(AccessLevel.NONE)
    private LocalDate releaseDate; // дата релиза
    Duration duration; // продолжительность фильма

    public void setReleaseDate(LocalDate releaseDate) throws ValidationException {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быль раньше декабря 1895 года!");
        } else {
            this.releaseDate = releaseDate;
        }
    }

    public Film(int id, String name, String description, LocalDate releaseDate, Duration duration) throws ValidationException {
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.description = description;
        setReleaseDate(releaseDate);

    }

}
