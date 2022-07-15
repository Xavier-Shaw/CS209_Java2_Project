package com.my.springboottest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.my.springboottest.entity.Answer;
import com.my.springboottest.entity.Question;
import com.my.springboottest.entity.Tag;
import com.my.springboottest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@RestController
@RequestMapping("/webScrap")
public class WebScrapHandler {

    static String URL_first = "https://api.stackexchange.com/2.3/questions?page=";
    static String URL_second = "&pagesize=100&fromdate=1335830400&todate=1651363200&tagged=java&site=stackoverflow&key=JlU1*Bmjee)bNnXDDftzlA((";

    static String URL_ans_1 = "https://api.stackexchange.com/2.3/answers?page=";
    static String URL_ans_2 = "&pagesize=100&fromdate=1335830400&todate=1651363200&site=stackoverflow&key=JlU1*Bmjee)bNnXDDftzlA((";

    @Autowired
    private com.my.springboottest.repository.tagRepository tagRepository;

    @Autowired
    private com.my.springboottest.repository.questionRepository questionRepository;

    @Autowired
    private com.my.springboottest.repository.userRepository userRepository;

    @Autowired
    private com.my.springboottest.repository.answerRepository answerRepository;


    @RequestMapping("/start")
    public List<Tag> storeDataToDatabase() throws IOException {
        List<Q> qList = scrap();
        List<User> users = new LinkedList<>();
        List<Question> questions = new LinkedList<>();
        List<Tag> tags = new LinkedList<>();

        int index = 1;

        for (Q q :
                qList) {
            for (item i :
                    q.items) {

                User user = new User(i.owner.user_id, i.owner.display_name, i.owner.reputation);
                users.add(user);

                Date date_util = new Date(i.creation_date * 1000);
                java.sql.Date date = new java.sql.Date(date_util.getTime());

                Question question = new Question(i.question_id, date, i.view_count, i.answer_count, i.owner.user_id);
                questions.add(question);

                for (String name:
                        i.tags) {
                    Tag tag = new Tag(index++, name, i.title, date, i.question_id);
                    tags.add(tag);
                }
            }
        }

        userRepository.saveAll(users);
        questionRepository.saveAll(questions);
        tagRepository.saveAll(tags);

        return tags;
    }

    @RequestMapping("/answer")
    public List<A> storeAnswerToDatabase() throws IOException {
        List<A> aList = scrapAnswer();
        List<User> users = new LinkedList<>();
        List<Answer> answers = new LinkedList<>();

        for (A a :
                aList) {
            for (a_item i :
                    a.items) {
                User user = new User(i.owner.user_id, i.owner.display_name, i.owner.reputation);
                users.add(user);

                Date date_util = new Date(i.creation_date * 1000);
                java.sql.Date date = new java.sql.Date(date_util.getTime());

                Answer answer = new Answer(i.answer_id, date, i.question_id, i.score, i.owner.user_id);
                answers.add(answer);
            }
        }

        userRepository.saveAll(users);
        answerRepository.saveAll(answers);

        return aList;
    }


    public static List<A> scrapAnswer() throws IOException {

        List<A> answers = new ArrayList<>();
        int page = 1;
        int respondCode;

        do {
            URL url = new URL(URL_ans_1 + (page++) + URL_ans_2);
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
                Type type = new TypeToken<A>() {}.getType();
                A answer = gson.fromJson(content.toString(), type);
//                json_content.append(gson.toJson(question));
                answers.add(answer);
            }
        } while (respondCode == 200);

        return answers;
    }


    public static List<Q> scrap() throws IOException {

        List<Q> questions = new ArrayList<>();
        int page = 1;
        int respondCode;

        do {
            URL url = new URL(URL_first + (page++) + URL_second);
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
            }
        } while (respondCode == 200);

        return questions;
    }




    class Q {
        private item[] items;
    }

    class item {
        private String[] tags;
        private Owner owner;
        private long creation_date;
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

    class A {
        private a_item[] items;
    }

    class a_item{
        private Owner owner;
        private long creation_date;
        private int question_id;
        private int user_id;
        private int answer_id;
        private int score;
    }

}
