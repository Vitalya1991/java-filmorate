package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class User {

    private int id;
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Email is not valid")
    private String email;
    @NotNull(message = "Login can not be null")
    @NotBlank(message = "Login can not be blank")
    private String login;
    private String name;
    @PastOrPresent(message = "Birthday can not be in the future")
    private LocalDate birthday;
    @Setter(AccessLevel.NONE)
    private Set<Integer> friends = new HashSet<>();

    public void addFriends(int id) {
        this.friends.add(id);
    }

    public void deleteFriends(int id) {
        this.friends.remove(id);
    }

    public boolean containsFriend(Integer id) {
        return friends.contains(id);
    }

}