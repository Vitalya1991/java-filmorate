package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;
import javax.validation.Valid;
import java.util.*;

@RequestMapping("/users")
@RestController
@Slf4j
@Getter
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    UserValidator userValidatior;
    private int id = 1;

    @GetMapping
    public Collection<User> getAll() {
        log.info("Получен запрос на получение списка пользователей");
        return users.values();
    }


    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userValidatior.validate(user);
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        log.info("Вы - {}!", "добавили нового пользователя");
        user.setId(id);
        users.put(user.getId(), user);
        id++;
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        userValidatior.validate(user);
        log.info("Вы - {}!", " обновили данные для текущего пользователя");
        users.put(user.getId(), user);
        return user;
    }

}
