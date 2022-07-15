package com.my.springboottest.utils;

import java.sql.Date;

public class test {

    public static void main(String[] args) {
        Date date = new Date(1398314850L * 1000);
        java.util.Date date1 = new Date(1653552602L * 1000);
        System.out.println(date);
        System.out.println(date1);
    }
}
