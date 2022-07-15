package com.my.springboottest.repository;

import com.my.springboottest.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface answerRepository extends JpaRepository<Answer, Integer> {
}
