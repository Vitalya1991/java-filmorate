package ru.yandex.practicum.filmorate.model;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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

