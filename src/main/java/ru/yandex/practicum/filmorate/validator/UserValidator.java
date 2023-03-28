package ru.yandex.practicum.filmorate.validator;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;
import java.time.LocalDate;

@Component
public class UserValidator {
    public static boolean validation(User user) {
        if (user.getEmail() == null || (!user.getEmail().contains("@"))) {
            throw new ValidationException("email format is wrong");
        }
        if (user.getLogin().contains(" ") || user.getLogin().isEmpty()) {
            throw new ValidationException("login format is wrong");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday can't be");
        }
        return true;
    }
}

