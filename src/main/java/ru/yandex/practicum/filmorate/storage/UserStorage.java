package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

public interface UserStorage extends Storage<User> {
   Map<Integer, User> storage = new HashMap<>();
}