package com.crud_javatokotiln.controller

import com.crud_javatokotiln.dto.UserDto
import com.crud_javatokotiln.entity.User
import com.crud_javatokotiln.global.ApiResponse
import com.crud_javatokotiln.global.ApiResponse.Companion.created
import com.crud_javatokotiln.global.ApiResponse.Companion.ok
import com.crud_javatokotiln.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관련 API")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    @Operation(summary = "모든 사용자 조회", description = "등록된 모든 사용자를 조회합니다.")
    fun allUsers(): ResponseEntity<ApiResponse<List<User>>> {
        val users = userService.findAll()
        return ResponseEntity.ok(ok(users))
    }

    @Operation(summary = "특정 사용자 조회", description = "ID에 해당하는 사용자를 조회합니다.")
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<ApiResponse<User>> {
        val user = userService.findById(id)
        return ResponseEntity.ok(ok(user))
    }

    @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다.")
    @PostMapping
    fun createUser(@RequestBody userDto: UserDto): ResponseEntity<ApiResponse<User>> {
        val user = userService.create(userDto)
        return ResponseEntity(created(user), HttpStatus.CREATED)
    }

    @Operation(summary = "사용자 업데이트", description = "ID에 해당하는 사용자의 정보를 업데이트합니다.")
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userDto: UserDto): ResponseEntity<ApiResponse<User>> {
        val user = userService.update(id, userDto)
        return ResponseEntity.ok(ok(user))
    }

    @Operation(summary = "사용자 삭제", description = "ID에 해당하는 사용자를 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<ApiResponse<Void?>> {
        userService.delete(id)
        return ResponseEntity(ok(null), HttpStatus.NO_CONTENT)
    }
}
