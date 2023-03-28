package ru.yandex.practicum.filmorate.storage.database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.database.interfaces.LikeStorage;

import java.util.*;

@Component("LikeDbStorage")
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveLikes(Film film) {
        jdbcTemplate.update("DELETE FROM FILMS_LIKES WHERE FILM_ID = ?", film.getId());

        String sql = "INSERT INTO FILMS_LIKES (FILM_ID, USER_ID) VALUES(?, ?)";
        Set<Integer> likes = film.getUsersLikes();
        for (var like : likes) {
            jdbcTemplate.update(sql, film.getId(), like);
        }
    }

    @Override
    public void loadLikes(Film film) {
        String sql = "SELECT USER_ID FROM FILMS_LIKES WHERE FILM_ID = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, film.getId());
        film.clearLikes();
        while (sqlRowSet.next()) {
            film.addUserLike(sqlRowSet.getInt("USER_ID"));
        }
    }
}
