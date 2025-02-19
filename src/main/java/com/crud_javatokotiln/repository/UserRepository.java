package com.crud_javatokotiln.repository;

import com.crud_javatokotiln.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
