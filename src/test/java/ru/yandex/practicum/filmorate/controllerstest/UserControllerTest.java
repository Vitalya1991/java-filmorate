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
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    UserController userController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        userController.putUser(FilmUserTestData.user);
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
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/users")
                                .content("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"\",\"birthday\":\"1980-05-13\"}")
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
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .content("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"name\",\"name\":\"login\",\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"name\",\"name\":\"login\",\"birthday\":\"1980-05-13\"}"));
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
                                .content("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"\",\"birthday\":\"1980-05-13\"}")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("{\"id\":1,\"email\":\"lol@mail.ru\",\"login\":\"login\",\"name\":\"login\",\"birthday\":\"1980-05-13\"}"));
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

    @SpringBootTest
    @AutoConfigureMockMvc
    static
    class FilmControllerTest {
        @Autowired
        FilmController filmController;
        @Autowired
        private MockMvc mockMvc;

        @Test
        void findAll() throws Exception {
            filmController.putFilm(FilmUserTestData.film);
            mockMvc.perform(MockMvcRequestBuilders.get("/films"))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content()
                            .json("[{\"id\":1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"PT2H\"}]"));
        }

        @Test
        void createAllOk() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"PT2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content()
                            .json("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"PT2H\"}"));
        }

        @Test
        void createBadData() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"1891-10-13\",\"duration\":\"PT2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @Test
        void createEmptyName() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/films")
                                    .content("{\"id\"1,\"name\":\"\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"PT2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @Test
        void createTooLongDescription() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some long descriptionfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff\",\"releaseDate\":\"2020-10-13\",\"duration\":\"PT2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @Test
        void createBadDuration() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"P-T2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @Test
        void createNullDuration() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"ZERO\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @Test
        void putAllOk() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.put("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"PT2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(content()
                            .json("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"PT2H\"}"));
        }

        @Test
        void putBadData() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.put("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"1891-10-13\",\"duration\":\"PT2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @Test
        void putEmptyName() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.put("/films")
                                    .content("{\"id\"1,\"name\":\"\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"PT2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @Test
        void putTooLongDescription() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.put("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some long descriptionfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff\",\"releaseDate\":\"2020-10-13\",\"duration\":\"PT2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @Test
        void putBadDuration() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.put("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"P-T2H\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @Test
        void putNullDuration() throws Exception {
            mockMvc.perform(
                            MockMvcRequestBuilders.put("/films")
                                    .content("{\"id\"1,\"name\":\"New film\",\"description\":\"Some description\",\"releaseDate\":\"2020-10-13\",\"duration\":\"ZERO\"}")
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
            ;
        }

        @AfterEach
        void delete() {
            filmController.getFilms().clear();
        }
    }
}