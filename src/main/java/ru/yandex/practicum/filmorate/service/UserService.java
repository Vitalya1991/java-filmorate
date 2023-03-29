package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.database.interfaces.FriendStorage;
import ru.yandex.practicum.filmorate.storage.database.interfaces.UserDStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserService(@Qualifier("UserDbStorage") UserDStorage userStorage,
                       @Qualifier("FriendDbStorage") FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }


    public User create(User user) {
        validate(user);
        userStorage.add(user);
        log.info("Пользователь добавлен в коллекцию");
        return user;
    }


    public User update(User user) {
        validate(user);
        if (getIds().contains(user.getId())) {
            userStorage.replace(user);
            return userStorage.getById(user.getId());
        }
        log.error("Пользователь в коллекции не найден");
        throw new UserNotFoundException("Ошибка при обновлении: пользователь c id = " + user.getId() + " не найден");
    }

    public Collection<User> findAll() {
        log.info("Возвращен список пользователей");
        Collection<User> users = userStorage.getValues();
        users.forEach(friendStorage::loadFriends);
        return users;
    }

    public User getById(Integer userId) {
        if (getIds().contains(userId)) {
            User user = userStorage.getById(userId);
            friendStorage.loadFriends(user);
            return user;
        }
        log.error("Пользователь в коллекции не найден");
        throw new UserNotFoundException("Ошибка при поиске: пользователь id = " + userId + " не найден");
    }


    public User addFriend(Integer userId1, Integer userId2) {
        if (!getIds().contains(userId1)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при добавлении в друзья: пользователь c id = " + userId1 + " не найден");
        }
        if (!getIds().contains(userId2)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при добавлении в друзья: пользователь c id = " + userId2 + " не найден");
        }
        if (friendStorage.containsFriendship(userId2, userId1, false)) {
            friendStorage.updateFriendship(userId2, userId1, true, userId2, userId1);
        } else if (!friendStorage.containsFriendship(userId1, userId2, null)) {
            friendStorage.insertFriendship(userId1, userId2);
        }
        User user = userStorage.getById(userId1);
        friendStorage.loadFriends(user);
        return user;
    }


    public User deleteFriend(Integer userId1, Integer userId2) {
        if (!getIds().contains(userId1)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при удалении из друзей: пользователь c id = " + userId1 + " не найден");
        }
        if (!getIds().contains(userId2)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при удалении из друзей: пользователь c id = " + userId2 + " не найден");
        }
        if (friendStorage.containsFriendship(userId1, userId2, false)) {
            friendStorage.removeFriendship(userId1, userId2);
        } else if (friendStorage.containsFriendship(userId1, userId2, true)) {
            friendStorage.updateFriendship(userId2, userId1, false, userId1, userId2);
        } else if (friendStorage.containsFriendship(userId2, userId1, true)) {
            friendStorage.updateFriendship(userId2, userId1, false, userId2, userId1);
        }
        User user = userStorage.getById(userId1);
        friendStorage.loadFriends(user);
        return userStorage.getById(userId1);
    }


    public Collection<User> returnFriendCollection(Integer userId) {
        if (!getIds().contains(userId)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при поиске друзей: пользователь c id = " + userId + " не найден");
        }
        User user = userStorage.getById(userId);
        friendStorage.loadFriends(user);
        Set<Integer> temp = user.getFriends();
        return userStorage.getValues().stream()
                .filter(x -> temp.contains(x.getId()))
                .sorted(this::compare)
                .collect(Collectors.toList());
    }


    public Collection<User> returnCommonFriends(Integer userId1, Integer userId2) {
        if (!getIds().contains(userId1)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при поиске общих друзей: пользователь c id = " + userId1 + " не найден");
        }
        if (!getIds().contains(userId2)) {
            log.error("Пользователь в коллекции не найден");
            throw new UserNotFoundException("Ошибка при поиске общих друзей: пользователь c id = " + userId2 + " не найден");
        }
        User user1 = userStorage.getById(userId1);
        friendStorage.loadFriends(user1);
        User user2 = userStorage.getById(userId2);
        friendStorage.loadFriends(user2);
        Set<Integer> temp =  user1.getFriends()
                .stream()
                .filter(user2.getFriends()::contains)
                .collect(Collectors.toSet());

        return userStorage.getValues().stream()
                .filter(x -> temp.contains(x.getId()))
                .sorted(this::compare)
                .collect(Collectors.toList());

    }


    public void validate(@Valid User user) {
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