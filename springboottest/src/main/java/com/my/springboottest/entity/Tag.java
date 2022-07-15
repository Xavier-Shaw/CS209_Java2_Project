package com.my.springboottest.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Data
@Table(name = "tag", schema = "my_schema")
public class Tag {

    @Id
    private Integer id;
    private String name;
    private String title;
    private Date creation_date;
    private Integer question_id;

    public Tag(){

    }

    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(Integer id, String name, String title, Date creation_date, Integer question_id) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.creation_date = creation_date;
        this.question_id = question_id;
    }
}
