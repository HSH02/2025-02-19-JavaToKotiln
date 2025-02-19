package com.crud_javatokotiln.controller;

import com.crud_javatokotiln.dto.UserDto;
import com.crud_javatokotiln.entity.User;
import com.crud_javatokotiln.global.ApiResponse;
import com.crud_javatokotiln.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "모든 사용자 조회", description = "등록된 모든 사용자를 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(ApiResponse.ok(users));
    }

    @Operation(summary = "특정 사용자 조회", description = "ID에 해당하는 사용자를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(ApiResponse.ok(user));
    }

    @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody UserDto userDto) {
        User user = userService.create(userDto);
        return new ResponseEntity<>(ApiResponse.created(user), HttpStatus.CREATED);
    }

    @Operation(summary = "사용자 업데이트", description = "ID에 해당하는 사용자의 정보를 업데이트합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userService.update(id, userDto);
        return ResponseEntity.ok(ApiResponse.ok(user));
    }

    @Operation(summary = "사용자 삭제", description = "ID에 해당하는 사용자를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(ApiResponse.ok(null), HttpStatus.NO_CONTENT);
    }
}
