package com.my.springboottest.repository;

import com.my.springboottest.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface tagRepository extends JpaRepository<Tag, Integer> {
}
