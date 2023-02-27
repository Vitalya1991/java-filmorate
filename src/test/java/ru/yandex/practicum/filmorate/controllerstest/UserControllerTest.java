package ru.yandex.practicum.filmorate.controllerstest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.controller.ValidationAdvince;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    UserController userController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        userController.create(FilmUserTestData.user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("[{\"birthday\":\"1980-05-13\",\"name\":\"name\",\"id\":1,\"login\":\"login\",\"email\":\"lol@mail.ru\"}]"));
    }

    @Test
    void putAllOk() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .content("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1980-05-13\"}"));
    }

    @Test
    void putEmptyEmail() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .content("{\"id\"1,\"email\":\"\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void putBadEmail() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .content("{\"id\"1,\"email\":\"lol\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void putEmptyName() throws Exception {
        userController.create(FilmUserTestData.user);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .content("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"login\",\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"login\",\"birthday\":\"1980-05-13\"}"));
    }

    @Test
    void putBirthdayFuture() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .content("{\"id\"1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"2023-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void createAllOk() throws Exception {
        userController.create(FilmUserTestData.user);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .content("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("{\"id\":3,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1980-05-13\"}"));
    }

    @Test
    void createEmptyEmail() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .content("{\"id\"1,\"email\":\"\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void createBadEmail() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .content("{\"id\"1,\"email\":\"lol\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void createEmptyName() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .content("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":null,\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("{\"id\":5,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"login\",\"birthday\":\"1980-05-13\"}"));
    }

    @Test
    void createBirthdayFuture() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .content("{\"id\"1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"name\",\"birthday\":\"2023-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }


    @AfterEach
    void delete() {
        userController.getUsers().clear();
    }

}