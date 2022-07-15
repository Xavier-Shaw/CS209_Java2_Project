package com.my.springboottest.controller;


import com.my.springboottest.entity.SortTag;
import com.my.springboottest.entity.Tag;
import com.my.springboottest.repository.sortTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/sortTag")
public class SortTagHandler {

    @Autowired
    private sortTagRepository sortTagRepository;

    @GetMapping("/findMostTag")
    public List<SortTag> findMostTag(){
        List<SortTag> mostTag = sortTagRepository.findMostTag();
        return mostTag;
    }

    @GetMapping("/findMostKTag/{K}")
    public List<SortTag> findMostKTag(@PathVariable int K){
        List<SortTag> mostKTag = sortTagRepository.findMostKTag(K);
        return  mostKTag;
    }

    @GetMapping("/searchTag/{name}")
    public SortTag searchTag(@PathVariable String name){
        SortTag tag = sortTagRepository.searchTag(name);
        return tag;
    }


    @GetMapping("/searchRelativeTagWithDate/{name}/{fromDate}/{toDate}")
    public List<SortTag> searchRelativeTagWithDate(@PathVariable("name") String name, @PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate){
        List<SortTag> relativeTags = sortTagRepository.searchRelativeTagWithDate(name, Date.valueOf(fromDate), Date.valueOf(toDate));
        return relativeTags;
    }

    @GetMapping("/searchRelativeTagTopKWithDate/{name}/{K}/{fromDate}/{toDate}")
    public List<SortTag> searchRelativeTagTopKWithDate(@PathVariable("name") String name, @PathVariable("K") int K, @PathVariable("fromDate")String fromDate, @PathVariable("toDate")String toDate){
        List<SortTag> relativeTags = sortTagRepository.searchRelativeTagTopKWithDate(name, K, Date.valueOf(fromDate), Date.valueOf(toDate));
        return  relativeTags;
    }

    @GetMapping("/getTrendTopKWithDate/{fromDate}/{toDate}")
    public List<SortTag> getTrendTopKWithDate(@PathVariable("fromDate")String fromDate, @PathVariable("toDate")String toDate){
        List<SortTag> tags = sortTagRepository.getTrendTopKWithDate(Date.valueOf(fromDate), Date.valueOf(toDate));
        return tags;
    }

    @GetMapping("/getSingleTag/{fromDate}/{toDate}")
    public Integer getSingleTag(@PathVariable("fromDate")String fromDate, @PathVariable("toDate")String toDate){
        Integer sum = sortTagRepository.getSingleTag(Date.valueOf(fromDate), Date.valueOf(toDate));
        return sum;
    }

    @GetMapping("/getMultiTag/{fromDate}/{toDate}")
    public Integer getMultiTag(@PathVariable("fromDate")String fromDate, @PathVariable("toDate")String toDate){
        Integer sum = sortTagRepository.getMultiTag(Date.valueOf(fromDate), Date.valueOf(toDate));
        return sum;
    }
}
