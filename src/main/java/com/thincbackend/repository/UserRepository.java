package com.thincbackend.repository;

import com.thincbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserID(String UserID);
}
