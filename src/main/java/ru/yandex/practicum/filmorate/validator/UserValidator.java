package ru.yandex.practicum.filmorate.validator;

import lombok.experimental.UtilityClass;
import ru.yandex.practicum.filmorate.model.User;

@UtilityClass
public  class UserValidator {
    public void validater(User user) {
        if(user.getName().isBlank()){
            user.setName(user.getLogin());
        }
    }
}