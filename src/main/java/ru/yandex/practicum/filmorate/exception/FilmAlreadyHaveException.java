package ru.yandex.practicum.filmorate.exception;

public class FilmAlreadyHaveException extends RuntimeException {
    public FilmAlreadyHaveException(String message) {
        super(message);
    }
}
