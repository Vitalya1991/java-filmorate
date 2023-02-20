package ru.yandex.practicum.filmorate.model;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data

@AllArgsConstructor
public class User {

    private int id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\S*$")
    private String login;

    private String name;

    @Past
    private LocalDate birthday;

}

