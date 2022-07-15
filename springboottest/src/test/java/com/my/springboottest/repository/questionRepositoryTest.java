package com.my.springboottest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class questionRepositoryTest {

    @Autowired
    private questionRepository questionRepository;

    @Test
    void findAll(){
        System.out.println(questionRepository.findAll());
    }
}