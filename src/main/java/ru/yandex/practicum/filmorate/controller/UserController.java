package ru.yandex.practicum.filmorate.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.UserValidator;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@RequestMapping("/users")
@RestController
@Slf4j
@Getter
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
     UserValidator userValidator;
     private int IdCount;
    @GetMapping
    public Collection<User> findAll() {
        log.info("Получен запрос на получение списка пользователей");
        return users.values();
    }


    @PostMapping
    public User create(@Valid @RequestBody User user) {
        userValidator.validater(user);
        user.setId(IdCount);
        IdCount++;
        log.info("Вы - {}!", "добавили нового пользователя");
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User putUser(@Valid@RequestBody User user) {
        userValidator.validater(user);
        log.info("Вы - {}!", " обновили данные для текущего фильма");
        users.put(user.getId(), user);
        return user;
    }

}
