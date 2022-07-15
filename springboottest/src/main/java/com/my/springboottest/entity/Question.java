package com.my.springboottest.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Data
@Table(name = "question", schema = "my_schema")
public class Question {
    @Id
    private Integer question_id;
    private Date creation_date;
    private Integer view_count;
    private Integer answer_count;
    private Integer user_id;


    public Question() {
    }

    public Question(Integer id, Date creation_date, Integer view_count, Integer answer_count, Integer user_id) {
        this.question_id = id;
        this.creation_date = creation_date;
        this.view_count = view_count;
        this.answer_count = answer_count;
        this.user_id = user_id;
    }
}
