package com.crud_javatokotiln.service;


import com.crud_javatokotiln.dto.UserDto;
import com.crud_javatokotiln.entity.User;
import com.crud_javatokotiln.exception.NotFoundException;
import com.crud_javatokotiln.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Transactional
    public User create(UserDto userDto) {
        User user = new User(userDto.getName(), userDto.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, UserDto userDto) {
        User user = findById(id);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
