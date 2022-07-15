package com.my.springboottest.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@Data
@Table(name = "answer", schema = "my_schema")
public class Answer {

    @Id
    @Column(name = "answer_id")
    private Integer answer_id;

    private Date creation_date;
    private Integer question_id;
    private Integer score;
    private Integer user_id;

    public Answer() {
    }

    public Answer(Integer answer_id, Date creation_date, Integer question_id, Integer score, Integer user_id) {
        this.answer_id = answer_id;
        this.creation_date = creation_date;
        this.question_id = question_id;
        this.score = score;
        this.user_id = user_id;
    }
}
