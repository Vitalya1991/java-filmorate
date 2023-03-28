package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.database.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FilmorateApplication.class)
class ValidationTests {

    private JdbcTemplate jdbcTemplate;
    private static final String LOGIN = "dolore ullamco";

    UserService userService = new UserService(new UserDbStorage(jdbcTemplate),
            new FriendDbStorage(jdbcTemplate));
    FilmService filmService = new FilmService(new FilmDbStorage(jdbcTemplate),
            new UserDbStorage(jdbcTemplate),
            new GenreDbStorage(jdbcTemplate),
            new LikeDbStorage(jdbcTemplate));

    @Test
    void contextLoads() {
    }

    @Test
    public void userValidateLoginTest() throws ValidationException {
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin(LOGIN);
        user.setName("User1");
        user.setBirthday(LocalDate.now());
        Assertions.assertThrows(ValidationException.class, () -> userService.validate(user));
    }

    @Test
    public void userValidateNameTest() throws ValidationException {
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("User1");
        userService.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }

}
