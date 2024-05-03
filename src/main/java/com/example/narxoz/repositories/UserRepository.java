package com.example.narxoz.repositories;

import com.example.narxoz.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findBySkFk(String SkFk);
}
