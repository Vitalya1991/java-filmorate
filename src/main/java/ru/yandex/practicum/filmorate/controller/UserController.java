package ru.yandex.practicum.filmorate.controller;

import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Getter
public class UserController {
    private final Map<Integer, User> users = new HashMap<Integer, User>();

    @GetMapping("/users")
    public Collection<User> findAll() {
        log.info("Получен запрос на получение списка пользователей");
        return users.values();
    }

    private void validateUsers(@Valid User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());

        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        }
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        validateUsers(user);
        user.setId(users.size() + 1);
        log.info("Вы - {}!", "добавили нового пользователя");
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users")
    public User putUser(@Valid @RequestBody User user) {
        validateUsers(user);
        user.setId(users.size() + 1);
        log.info("Вы - {}!", " обновили данные для текущего фильма");
        users.put(user.getId(), user);
        return user;
    }

}
