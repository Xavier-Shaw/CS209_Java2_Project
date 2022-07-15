package com.my.springboottest.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user", schema = "my_schema")
public class User {

    @Id
    @Column(name = "user_id")
    private Integer user_id;

    private String display_name;
    private Integer reputation;

    public User() {
    }

    public User(Integer user_id, String display_name, Integer reputation) {
        this.user_id = user_id;
        this.display_name = display_name;
        this.reputation = reputation;
    }
}
