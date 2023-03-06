package ru.yandex.practicum.filmorate.model;

import java.time.LocalDate;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class Film {

    private int id;
    @NotNull(message = "Name of the film can not be null")
    @NotBlank(message = "Name of the film can not be blank")
    private final String name;
    @Size(message = "Description size of the film must be up 200 chars", min = 1, max = 200)
    private final String description;
    private final LocalDate releaseDate;
    @Positive(message = "Duration of the film can not be negative or zero")
    private final int duration;
    @JsonIgnore
    private Set<Integer> usersLikes;

    public void addUserLike(int userId) {
        this.usersLikes.add(userId);
    }
    public void deleteUserLike(int userId) {
        this.usersLikes.remove(userId);
    }

}