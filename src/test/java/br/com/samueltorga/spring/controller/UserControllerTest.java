package br.com.samueltorga.spring.controller;

import br.com.samueltorga.spring.config.SecurityConfig;
import br.com.samueltorga.spring.controller.dto.user.NewUserRequest;
import br.com.samueltorga.spring.controller.dto.user.UserResponse;
import br.com.samueltorga.spring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
@Import({SecurityConfig.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsersReturnsPagedModel() throws Exception {
        when(userService.getAllUsers(any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    void getUserByIdReturnsUserWhenFound() throws Exception {
        UserResponse user = new UserResponse();
        user.setId(1);
        when(userService.getUserById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getUserByIdThrowsExceptionWhenNotFound() throws Exception {
        when(userService.getUserById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUserReturnsCreatedUser() throws Exception {
        NewUserRequest newUserRequest = new NewUserRequest();
        newUserRequest.setUsername("testuser");
        newUserRequest.setPassword("password");
        newUserRequest.setFullName("Test User");
        newUserRequest.setEmail("test@example.com");
        newUserRequest.setPhone("1234567890");
        newUserRequest.setAddress("123 Test St");

        UserResponse user = new UserResponse();
        user.setUsername("testuser");
        when(userService.createUser(newUserRequest)).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}