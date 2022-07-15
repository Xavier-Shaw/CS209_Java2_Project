package com.my.springboottest.entity;


import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
public class SortTag {

    @Id
    private String name;

    private Integer count;

    public SortTag() {
    }
}
