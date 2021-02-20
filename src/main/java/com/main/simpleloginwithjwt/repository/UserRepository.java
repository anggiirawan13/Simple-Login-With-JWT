package com.main.simpleloginwithjwt.repository;

import com.main.simpleloginwithjwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findById(int id);

    public User findByUsername(String username);
}
