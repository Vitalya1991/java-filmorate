package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;
import javax.validation.Valid;
import java.util.*;

@RequestMapping("/users")
@RestController
@Slf4j
@Getter
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    final UserValidator userValidatior;
    private int id = 1;

    public UserController(UserValidator userValidatior) {
        this.userValidatior = userValidatior;
    }

    @GetMapping
    public Collection<User> getAll() {
        log.info("Получен запрос на получение списка пользователей");
        return users.values();
    }


    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userValidatior.validate(user);
        log.info("Вы - {}!", "добавили нового пользователя");
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        return user;
    }


    @SneakyThrows
    @PutMapping
    public User put(@Valid @RequestBody User user) {
        userValidatior.validate(user);
        if (!users.containsKey(user.getId())){throw new ValidationAdvince();
        }
        log.info("Вы - {}!", " обновили данные для текущего пользователя");
        users.put(user.getId(), user);
        return user;
    }

}
