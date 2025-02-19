package com.crud_javatokotiln.controller;

import com.crud_javatokotiln.dto.UserDto;
import com.crud_javatokotiln.entity.User;
import com.crud_javatokotiln.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllUsers() throws Exception {
        // ID 포함 생성자 사용
        List<User> users = List.of(new User(1L, "홍길동", "hong@example.com"));
        Mockito.when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("홍길동"))
                .andExpect(jsonPath("$.data[0].email").value("hong@example.com"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void getUser() throws Exception {
        // ID 포함 생성자 사용
        User user = new User(1L, "홍길동", "hong@example.com");
        Mockito.when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("홍길동"))
                .andExpect(jsonPath("$.data.email").value("hong@example.com"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void createUser() throws Exception {
        // 클라이언트에서 전달받은 DTO는 ID가 null일 수 있으므로 그대로 사용
        UserDto userDto = new UserDto(null, "홍길동", "hong@example.com");
        // 생성자에서 ID 포함 (테스트용으로 1L 할당)
        User createdUser = new User(1L, "홍길동", "hong@example.com");
        Mockito.when(userService.create(any(UserDto.class))).thenReturn(createdUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("홍길동"))
                .andExpect(jsonPath("$.message").value("Created"));
    }

    @Test
    void updateUser() throws Exception {
        UserDto userDto = new UserDto(null, "홍길동 수정", "hong_updated@example.com");
        // 업데이트된 결과에도 ID 포함
        User updatedUser = new User(1L, "홍길동 수정", "hong_updated@example.com");
        Mockito.when(userService.update(eq(1L), any(UserDto.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.data.name").value("홍길동 수정"))
                .andExpect(jsonPath("$.data.email").value("hong_updated@example.com"))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    void deleteUser() throws Exception {
        Mockito.doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
}
