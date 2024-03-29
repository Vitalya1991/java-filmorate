package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public User create(User user) throws ValidationException {
        validate(user);
        userStorage.add(user);
        log.info("Пользователь добавлен в коллекцию");
        return user;
    }


    public User update(User user) throws ValidationException{
        validate(user);
        if (userStorage.users.containsKey(user.getId())) {
            return userStorage.replace(user);
        }
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при обновлении: пользователь c id = " + user.getId() + " не найден");
    }

    public Collection<User> findAll() {
        log.info("Возвращен список пользователей");
        return userStorage.getValues();
    }

    public User getById(Integer userId) {
        if (userStorage.users.containsKey(userId)) {
            return userStorage.getById(userId);
        }
        log.error("Пользователь в коллекции не найден");
        throw new UserNotFoundException("Ошибка при поиске: пользователь id = " + userId + " не найден");
    }


    public User addFriend(Integer userId1, Integer userId2) {
        if (!userStorage.users.containsKey(userId1)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при добавлении в друзья: пользователь c id = " + userId1 + " не найден");
        }
        if (!userStorage.users.containsKey(userId2)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при добавлении в друзья: пользователь c id = " + userId2 + " не найден");
        }
        userStorage.getById(userId1).addFriends(userId2);
        userStorage.getById(userId2).addFriends(userId1);
        return userStorage.getById(userId1);
    }


    public User deleteFriend(Integer userId1, Integer userId2) {
        if (!userStorage.users.containsKey(userId1)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при удалении из друзей: пользователь c id = " + userId1 + " не найден");
        }
        if (!userStorage.users.containsKey(userId2)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при удалении из друзей: пользователь c id = " + userId2 + " не найден");
        }
        userStorage.getById(userId1).deleteFriends(userId2);
        userStorage.getById(userId2).deleteFriends(userId1);
        return userStorage.getById(userId1);
    }


    public Collection<User> returnFriendCollection(Integer userId) {
        if (!userStorage.users.containsKey(userId)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при поиске друзей: пользователь c id = " + userId + " не найден");
        }
        Set<Integer> temp = userStorage.getById(userId).getFriends();
        return userStorage.getValues().stream()
                .filter(x -> temp.contains(x.getId()))
                .sorted(this::compare)
                .collect(Collectors.toList());
    }


    public Collection<User> returnCommonFriends(Integer userId1, Integer userId2) {
        if (!userStorage.users.containsKey(userId1)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при поиске общих друзей: пользователь c id = " + userId1 + " не найден");
        }
        if (!userStorage.users.containsKey(userId2)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при поиске общих друзей: пользователь c id = " + userId2 + " не найден");
        }
        Set<Integer> temp = userStorage.getById(userId1).getFriends()
                .stream()
                .filter(userStorage.getById(userId2).getFriends()::contains)
                .collect(Collectors.toSet());

        return userStorage.getValues().stream()
                .filter(x -> temp.contains(x.getId()))
                .sorted(this::compare)
                .collect(Collectors.toList());

    }


    public void validate(@Valid User user) throws ValidationException {
        if (user.getLogin().contains(" ")) {
            log.error("В логине пользователя есть пробел");
            throw new ValidationException("Не пройдена валидация пользователя по логину: " + user.getLogin());
        }
        if ((user.getName() == null) || (user.getName().isBlank())) {
            log.info("Пользователю в качестве имени присвоен логин");
            user.setName(user.getLogin());
        }
    }

    private Collection<Integer> getIds() {
        return userStorage.getValues().stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    private int compare(User first, User last) {
        return Integer.compare(first.getId(), last.getId());
    }
}