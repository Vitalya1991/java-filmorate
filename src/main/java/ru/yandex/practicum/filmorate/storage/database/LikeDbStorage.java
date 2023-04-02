package ru.yandex.practicum.filmorate.storage.database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.database.interfaces.LikeStorage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component("LikeDbStorage")
public class LikeDbStorage implements LikeStorage {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int[] saveLikes(Film film) {
        int[] likes = jdbcTemplate.batchUpdate(
                "INSERT INTO FILMS_LIKES (FILM_ID, USER_ID) VALUES(?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, film.getId());
                        ps.setInt(2, film.getLikesCount());
                    }

                    public int getBatchSize() {
                        return film.getLikesCount();
                    }
                });
        return likes;
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
