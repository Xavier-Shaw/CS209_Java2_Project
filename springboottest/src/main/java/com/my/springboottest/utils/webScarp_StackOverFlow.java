package com.my.springboottest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.my.springboottest.controller.TagHandler;
import com.my.springboottest.controller.WebScrapHandler;
import com.my.springboottest.entity.Tag;
import com.my.springboottest.repository.tagRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;


public class webScarp_StackOverFlow {

    static String URL_first = "https://api.stackexchange.com/2.3/questions?page=";
    static String URL_second = "&pagesize=100&fromdate=1640995200&todate=1651363200&tagged=java&site=stackoverflow&key=JlU1*Bmjee)bNnXDDftzlA((";

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
                Type type = new TypeToken<Q>() {
                }.getType();
                Q question = gson.fromJson(content.toString(), type);
//                json_content.append(gson.toJson(question));
                questions.add(question);
                System.out.println(gson.toJson(question));
                page++;
            }
        } while (respondCode == 200);

        return questions;
    }

    public static void main(String[] args) throws IOException {

        List<Q> qList = scrap();

    }

    class Q {
        private item[] items;
    }

    class item {
        private String[] tags;
        private Owner owner;
        private int creation_date;
        private int question_id;
        private int view_count;
        private int answer_count;
        private String title;
    }

    class Owner {
        private int user_id;
        private String display_name;
        private int reputation;
    }
}

