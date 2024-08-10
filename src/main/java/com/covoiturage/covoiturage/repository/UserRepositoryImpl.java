package com.covoiturage.covoiturage.repository;

import com.covoiturage.covoiturage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryImpl extends JpaRepository<User, Integer> {
    User findByFirstName(String firstName);

}
