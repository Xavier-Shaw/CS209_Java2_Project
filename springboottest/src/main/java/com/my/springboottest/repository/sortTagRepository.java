package com.my.springboottest.repository;


import com.my.springboottest.entity.SortTag;
import com.my.springboottest.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.Table;
import java.sql.Date;
import java.util.List;


public interface sortTagRepository extends JpaRepository<SortTag, String> {

    @Query(value = "select name, count(*) as count from my_schema.tag group by name order by count desc limit 1", nativeQuery = true)
    public List<SortTag> findMostTag();


    @Query(value = "select name, count(*) as count from my_schema.tag group by name order by count desc limit :K", nativeQuery = true)
    public List<SortTag> findMostKTag(@PathVariable("K") int K);


    @Query(value = "select name, count(*) as count from my_schema.tag where name = :name group by name", nativeQuery = true)
    public SortTag searchTag(@PathVariable("name") String name);


    @Query(value = "select name, count(*) as count " +
            "from my_schema.tag where name like CONCAT('%',:name,'%') " +
            "and creation_date >= :fromDate and creation_date <= :toDate " +
            "group by name order by count desc", nativeQuery = true)
    public List<SortTag> searchRelativeTagWithDate(@PathVariable("name") String name, @PathVariable("fromDate")Date fromDate, @PathVariable("toDate")Date toDate);

    @Query(value = "select name, count(*) as count " +
            "from my_schema.tag where name like CONCAT('%',:name,'%') " +
            "and creation_date >= :fromDate and creation_date <= :toDate" +
            " group by name order by count desc limit :K", nativeQuery = true)
    public List<SortTag> searchRelativeTagTopKWithDate(@PathVariable("name") String name, @PathVariable("K") int K, @PathVariable("fromDate")Date fromDate, @PathVariable("toDate")Date toDate);

    @Query(value = "select name, count(*) as count from (\n" +
            "              select * from my_schema.tag where name != 'java' \n" +
            "                  ) as t\n" +
            "where creation_date between :fromDate and :toDate \n" +
            "group by name\n" +
            "order by count desc", nativeQuery = true)
    public List<SortTag> getTrendTopKWithDate(@PathVariable("fromDate")Date fromDate, @PathVariable("toDate")Date toDate);

    @Query(value = "select sum(count) as sum from (\n" +
            "              select name, count(*) as count from my_schema.tag where creation_date between :fromDate and :toDate group by name\n" +
            "                  ) as t where count = 1", nativeQuery = true)
    public Integer getSingleTag(@PathVariable("fromDate")Date fromDate, @PathVariable("toDate")Date toDate);

    @Query(value = "select sum(count) as sum from (\n" +
            "              select name, count(*) as count from my_schema.tag where creation_date between :fromDate and :toDate group by name\n" +
            "                  ) as t where count > 1", nativeQuery = true)
    public Integer getMultiTag(@PathVariable("fromDate")Date fromDate, @PathVariable("toDate")Date toDate);
}
