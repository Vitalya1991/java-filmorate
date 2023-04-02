package ru.yandex.practicum.filmorate.database;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.database.interfaces.FilmDStorage;
import ru.yandex.practicum.filmorate.storage.database.interfaces.GenreStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = FilmorateApplication.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class GenreDbStorageTest {
    private final FilmDStorage filmStorage;
    private final GenreStorage genreStorage;
    private int countRec;

    @BeforeEach
    void setUp() {
        countRec = genreStorage.getValues().size();
    }


    @Test
    void saveGenre() {
        Film film1 = getExpFilm1();
        filmStorage.add(film1);
        genreStorage.saveGenre(film1);
        Film film2 = getExpFilm2();
        film2.setId(1);
        genreStorage.loadGenre(film2);
        Set<Genre> genres = film2.getGenres();
        assertTrue(genres.contains(new Genre(1, "Комедия")));
        assertTrue(genres.contains(new Genre(2, "Драма")));
    }

    @Test
    void loadGenre() {
        Film film2 = getExpFilm2();
        filmStorage.add(film2);
        Film film1 = getExpFilm1();
        film1.setId(1);
        genreStorage.loadGenre(film2);
        assertEquals(new HashSet<>(), film2.getGenres());
    }

    @Test
    void deleteGenre() {
        Film film = getExpFilm1();
        filmStorage.add(film);
        genreStorage.saveGenre(film);
        genreStorage.deleteGenre(film);
        assertEquals(new HashSet<>(), filmStorage.getById(1).getGenres());
    }


    private Genre getExpGenre1(Integer id) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("Genre1");
        return genre;
    }

    private Genre getExpGenre2(Integer id) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("Genre2");
        return genre;
    }

    private Film getExpFilm1() {
        Film film = new Film();
        film.setId(1);
        film.setName("Film1");
        film.setDescription("DESCRIPTION1");
        film.setReleaseDate(LocalDate.of(2020, 3, 3));
        film.setDuration(100);

        Rating rating = new Rating();
        rating.setId(1);
        film.setMpa(rating);

        Genre genre1 = new Genre();
        genre1.setId(1);
        Genre genre2 = new Genre();
        genre2.setId(2);
        film.setGenres(Set.of(genre1, genre2));
        return film;
    }

    private Film getExpFilm2() {
        Film film = new Film();
        film.setId(2);
        film.setName("Film2");
        film.setDescription("DESCRIPTION2");
        film.setReleaseDate(LocalDate.of(2010, 1, 3));
        film.setDuration(90);
        Rating rating = new Rating();
        rating.setId(2);
        film.setMpa(rating);
        return film;
    }
}
