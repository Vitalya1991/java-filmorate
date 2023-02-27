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

    final UserValidator userValidatior;
    private int id = 1;

    public UserController(UserValidator userValidatior) {
        this.userValidatior = userValidatior;
    }

    @GetMapping
    public Collection<User> findAll() {
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

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationAdvince {
        userValidatior.validate(user);
        if (!users.containsKey(user.getId())){throw new ValidationAdvince();
        }
        log.info("Вы - {}!", " обновили данные для текущего пользователя");
        users.put(user.getId(), user);
        return user;
    }

}
