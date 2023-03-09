package ru.yandex.practicum.filmorate.exception;

public class UserAlreadyHaveException extends RuntimeException {
    public UserAlreadyHaveException(String message) {
        super(message);
    }
}
