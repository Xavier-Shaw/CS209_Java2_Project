package com.my.springboottest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class tagRepositoryTest {

    @Autowired
    private tagRepository tagRepository;

    @Test
    void findALL(){
        System.out.println(tagRepository.findAll());
    }
}