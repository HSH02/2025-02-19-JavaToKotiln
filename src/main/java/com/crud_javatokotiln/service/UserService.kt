package com.crud_javatokotiln.service


import com.crud_javatokotiln.dto.UserDto
import com.crud_javatokotiln.entity.User
import com.crud_javatokotiln.exception.NotFoundException
import com.crud_javatokotiln.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
open class UserService(
    private val userRepository: UserRepository
) {

    fun findAll(): List<User> = userRepository.findAll().filterNotNull()

    fun findById(id: Long): User =
        userRepository.findById(id)
            .orElseThrow { NotFoundException("User not found with id: $id") }!!

    @Transactional
    open fun create(userDto: UserDto): User {
        val name = userDto.name ?: throw IllegalArgumentException("Name cannot be null")
        val email = userDto.email ?: throw IllegalArgumentException("Email cannot be null")
        val user = User(email, name)
        return userRepository.save(user)
    }

    @Transactional
    open fun update(id: Long, userDto: UserDto): User {
        val user = findById(id)
        user.name = userDto.name ?: user.name
        user.email = userDto.email ?: user.email
        return userRepository.save(user)
    }

    @Transactional
    open fun delete(id: Long) {
        userRepository.deleteById(id)
    }
}
