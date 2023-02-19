package ru.yandex.practicum.filmorate.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data

@AllArgsConstructor
public class User {
    @Email(message = ("Не является почтовым адресом"))
    @NotBlank(message = ("Почтовый адрес не может быть пустым"))
    @NotNull(message = ("Почтовый адрес не может быть пустым"))
    private int id;
    private String email;
    private String name;
    @Past
    private LocalDate birthdate;

    @NotBlank(message = ("Логин не может быть пустым"))
    @NotNull(message = ("Логин адрес не может быть пустым"))
    private String login;

}

