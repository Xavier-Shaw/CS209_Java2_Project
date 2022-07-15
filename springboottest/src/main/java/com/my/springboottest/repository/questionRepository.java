package com.my.springboottest.repository;

import com.my.springboottest.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.util.List;

public interface questionRepository extends JpaRepository<Question, Integer> {

    @Query(value = "select question_id, creation_date, view_count, answer_count, user_id\n" +
            "from my_schema.question", nativeQuery = true)
    public List<Question> getQuestions();


    @Query(value = "select count(*) as count\n" +
            "from (select * from my_schema.question \n" +
            "where view_count > 10000) as t", nativeQuery = true)
    public Integer getHighViewQuestion();

    @Query(value = "select count(*) as count\n" +
            "from (select * from my_schema.question \n" +
            "where view_count between 1000 and 10000) as t", nativeQuery = true)
    public Integer getMidViewQuestion();

    @Query(value = "select count(*) as count\n" +
            "from (select * from my_schema.question \n" +
            "where view_count < 1000) as t", nativeQuery = true)
    public Integer getLowViewQuestion();


    @Query(value = "select distinct t.question_id, title, t.creation_date, view_count\n" +
            "from my_schema.question\n" +
            "join my_schema.tag t on question.question_id = t.question_id where t.creation_date between :fromDate and :toDate order by view_count desc", nativeQuery = true)
    public List<Question> getHotQuestion();
}
