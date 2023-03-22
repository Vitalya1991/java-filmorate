package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.ErrorResponse;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws UserNotFoundException, ValidationException {
        return userService.update(user);
    }

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable("id") Integer id) throws UserNotFoundException {
        return userService.getById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) throws UserNotFoundException {
        return userService.addFriend(id,friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) throws UserNotFoundException {
        return userService.deleteFriend(id,friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> findUserFriends(@PathVariable("id") Integer id) throws UserNotFoundException {
        return userService.returnFriendCollection(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> findUsersCommonFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) throws UserNotFoundException {
        return userService.returnCommonFriends(id,otherId);
    }
}