package com.example.productcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.productcrud.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Ubah jadi seperti ini agar tidak merah di Controller
    User findByUsername(String username);
}