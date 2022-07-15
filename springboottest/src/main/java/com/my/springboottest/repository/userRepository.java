package com.my.springboottest.repository;

import com.my.springboottest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User, Integer> {

}
