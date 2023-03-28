package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component("InMemoryUserStorage")
public class InMemoryUserStorage extends AbstractInMemoryStorage<User> implements UserStorage {
    private int id = 1;

    @Override
    public User add(User user) {
        user.setId(id);

        storage.put(id, user);
        id++;
        return user;
    }

    @Override
    public User replace(User user) {

        storage.replace(user.getId(), user);
        return user;}

    @Override
    public User delete(User user) {
        storage.remove(user.getId());
        return user;
    }

    @Override
    public User getById(int id) {
        return storage.get(id);
    }

    @Override
    public Collection<User> getValues() {
        return storage.values()
                .stream()
                .sorted(this::compare)
                .collect(Collectors.toList());
    }

    private int compare(User first, User last) {
        return Integer.compare(first.getId(), last.getId());
    }
}