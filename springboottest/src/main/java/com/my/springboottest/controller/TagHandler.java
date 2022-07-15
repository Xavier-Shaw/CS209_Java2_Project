package com.my.springboottest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.my.springboottest.entity.Tag;
import com.my.springboottest.repository.tagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.zip.GZIPInputStream;

@RestController
@RequestMapping("/tag")
public class TagHandler {

    static String URL_first = "https://api.stackexchange.com/2.3/questions?page=";
    static String URL_second = "&pagesize=100&fromdate=1640995200&todate=1642000000&tagged=java&site=stackoverflow&key=JlU1*Bmjee)bNnXDDftzlA((";

    @Autowired
    private tagRepository tagRepository;

    @GetMapping("/findAll")
    public List<Tag> findALL(){
        return tagRepository.findAll();
    }

    @GetMapping("/findTag")
    public List<String> findTopKTags() {
        List<String> tags = new LinkedList<>();
        for (int i = 1; i <= 5; i++) {
            tags.add(tagRepository.findById(i).get().getName());
        }

        return tags;
    }


    @RequestMapping("/tag")
    public List<Tag> addAll() throws IOException {
        List<Tag> tagList = new LinkedList<>();
        List<Q> qList = scrap();
        int index = 1;

        for (Q q :
                qList) {
            for (item i :
                    q.items) {
                for (int j = 0; j < Math.min(i.tags.length, 4); j++) {
                    String tag = i.tags[j];
                    Tag t = new Tag(index++, tag);
                    tagList.add(t);
                    tagRepository.save(t);
                }
            }
        }

        return tagList;
    }


    public static List<Q> scrap() throws IOException {

        List<Q> questions = new ArrayList<>();
        int page = 1;
        int respondCode;
//        StringBuilder json_content = new StringBuilder();

        do {
            URL url = new URL(URL_first + page + URL_second);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            respondCode = conn.getResponseCode();

            if (respondCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream())));
                StringBuilder content = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                bufferedReader.close();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type type = new TypeToken<Q>() {}.getType();
                Q question = gson.fromJson(content.toString(), type);
//                json_content.append(gson.toJson(question));
                questions.add(question);
                page++;
            }
        } while (respondCode == 200);

        return questions;
    }


    class Q {
        private item[] items;
    }

    class item {
        private String[] tags;
        private int creation_date;
    }

}
