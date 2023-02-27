package ru.yandex.practicum.filmorate.validator;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

@Component
public class UserValidator {
    public void validate (User user) {
        if(user.getName() == null){
            user.setName(user.getLogin());}
        }
}

