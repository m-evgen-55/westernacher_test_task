package com.westernacher.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.westernacher.test.controller.request.SortUserDetailsRequest;
import com.westernacher.test.controller.request.UserRequest;
import com.westernacher.test.model.SortUserDetails;
import com.westernacher.test.model.User;
import com.westernacher.test.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * This class here just to show one more way of unit testing.
 */
class UserControllerTest {

    private UserService userService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser() throws Exception {
        when(userService.createUser(any())).thenReturn(getUser());

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUserRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("1"))
                .andExpect(jsonPath("$.responseBody.firstName").value("Ivan"))
                .andExpect(jsonPath("$.responseBody.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.responseBody.email").value("ivanov@yandex.ru"))
                .andExpect(jsonPath("$.responseBody.birthDate").value("59970920400000"));
    }

    @Test
    void updateUser() throws Exception {
        when(userService.updateUser(anyLong(), any())).thenReturn(getUser());

        mockMvc.perform(put("/user/update?userId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getUserRequest())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("1"))
                .andExpect(jsonPath("$.responseBody.firstName").value("Ivan"))
                .andExpect(jsonPath("$.responseBody.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.responseBody.email").value("ivanov@yandex.ru"))
                .andExpect(jsonPath("$.responseBody.birthDate").value("59970920400000"));
    }

    @Test
    void findUserById() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(getUser());

        mockMvc.perform(get("/user/findById?userId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.id").value("1"))
                .andExpect(jsonPath("$.responseBody.firstName").value("Ivan"))
                .andExpect(jsonPath("$.responseBody.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.responseBody.email").value("ivanov@yandex.ru"))
                .andExpect(jsonPath("$.responseBody.birthDate").value("59970920400000"));
    }

    @Test
    void findAllUsers() throws Exception {
        when(userService.findAllUsers(any())).thenReturn(getUserList());

        mockMvc.perform(post("/user/findAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SortUserDetailsRequest().setSortUserDetails(SortUserDetails.SORT_BY_BIRTH_DATE_ASC))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.responseBody.[0].id").value("1"))
                .andExpect(jsonPath("$.responseBody.[0].firstName").value("Ivan"))
                .andExpect(jsonPath("$.responseBody.[0].lastName").value("Ivanov"))
                .andExpect(jsonPath("$.responseBody.[0].email").value("ivanov@yandex.ru"))
                .andExpect(jsonPath("$.responseBody.[0].birthDate").value("59970920400000"))
                .andExpect(jsonPath("$.responseBody.[1].id").value("2"))
                .andExpect(jsonPath("$.responseBody.[1].firstName").value("Petr"))
                .andExpect(jsonPath("$.responseBody.[1].lastName").value("Petrov"))
                .andExpect(jsonPath("$.responseBody.[1].email").value("petrov@yandex.ru"))
                .andExpect(jsonPath("$.responseBody.[1].birthDate").value("60440763600000"));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/user/delete?userId=1"))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.success").value("true"));
    }

    private User getUser() {
        return new User()
                .setId(1)
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setEmail("ivanov@yandex.ru")
                .setBirthDate(new Date(1970, Calendar.MAY, 28));
    }

    private UserRequest getUserRequest() {
        return new UserRequest()
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setEmail("ivanov@yandex.ru")
                .setBirthDate(new Date(1970, Calendar.MAY, 28));
    }

    private List<User> getUserList() {
        return List.of(
                new User()
                        .setId(1)
                        .setFirstName("Ivan")
                        .setLastName("Ivanov")
                        .setEmail("ivanov@yandex.ru")
                        .setBirthDate(new Date(1970, Calendar.MAY, 28)),
                new User()
                        .setId(2)
                        .setFirstName("Petr")
                        .setLastName("Petrov")
                        .setEmail("petrov@yandex.ru")
                        .setBirthDate(new Date(1985, Calendar.APRIL, 17))
        );
    }
}