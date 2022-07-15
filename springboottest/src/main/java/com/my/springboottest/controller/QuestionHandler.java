package com.my.springboottest.controller;


import com.my.springboottest.entity.Question;
import com.my.springboottest.repository.questionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionHandler {

    @Autowired
    private questionRepository questionRepository;

    @GetMapping("/getQuestions")
    public List<Question> getQuestions(){
        List<Question> questions = questionRepository.getQuestions();
        return questions;
    }

    @GetMapping("/getHighViewQuestion")
    public Integer getHighViewQuestion(){
        Integer questions = questionRepository.getHighViewQuestion();
        return questions;
    }

    @GetMapping("/getMidViewQuestion")
    public Integer getMidViewQuestion(){
        Integer questions = questionRepository.getMidViewQuestion();
        return questions;
    }

    @GetMapping("/getLowViewQuestion")
    public Integer getLowViewQuestion(){
        Integer questions = questionRepository.getLowViewQuestion();
        return questions;
    }

//    @GetMapping("/getHotQuestion")
//    public List<Question> getHotQuestion(@PathVariable){
//
//    }
}
