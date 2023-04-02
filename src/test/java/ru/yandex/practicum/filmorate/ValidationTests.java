package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.database.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.database.UserDbStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FilmorateApplication.class)
class ValidationTests {

    JdbcTemplate jdbcTemplate;

    UserService userService = new UserService(new UserDbStorage(jdbcTemplate),
            new FriendDbStorage(jdbcTemplate));

    @Test
    void contextLoads() {
    }


    @Test
    void userValidateNameTest() throws ValidationException {
        User user = new User();
        user.setEmail("mail@mail.ru");
        user.setLogin("User1");
        userService.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }

}
