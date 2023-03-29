package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@RequiredArgsConstructor
@Getter
@Setter
public class Film {

    private int id;
    @NotBlank(message = "Name of the film can not be blank")
    private String name;
    @Size(message = "Description size of the film must be up 200 chars", min = 1, max = 200)
    private String description;
    @NotNull(message = "releaseDate of the film can not be null")
    private LocalDate releaseDate;
    @Positive(message = "Duration of the film can not be negative or zero")
    private int duration;
    private Rating mpa;
    private Set<Genre> genres = new TreeSet<>(Comparator.comparing(Genre::getId));

    @Setter(AccessLevel.NONE)
    private Set<Integer> usersLikes = new HashSet<>();

    public void addUserLike(int userId) {
        this.usersLikes.add(userId);
    }

    public void deleteUserLike(int userId) {
        this.usersLikes.remove(userId);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void clearGenre() {
        genres = new TreeSet<>(Comparator.comparing(Genre::getId));
    }

    public void clearLikes() {
        usersLikes.clear();
    }

    public int getLikesCount() {
        return usersLikes.size();
    }

    private int compare(Genre first, Genre last) {
        return Integer.compare(first.getId(), last.getId());
    }

    public Integer getId() {
        return id;
    }


}