package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@Slf4j
@RestController
public class GenreContoller {

    private final GenreService genreService;

    @Autowired
    public GenreContoller(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public Collection<Genre> findAll() {
        return genreService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre findGenre(@PathVariable("id") Integer id) {
        return genreService.getGenreById(id);
    }
}