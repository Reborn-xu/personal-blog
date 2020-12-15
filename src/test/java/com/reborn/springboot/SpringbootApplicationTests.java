package com.reborn.springboot;

import com.reborn.springboot.dao.UserMapper;
import javafx.scene.input.DataFormat;
import jdk.nashorn.internal.parser.DateParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

@SpringBootTest
class SpringbootApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void test2(){
        Person p1 = new Person();
        p1.name="p1";
        HashSet hashSet = new HashSet();
        for (int i=0;i<5;i++){
            p1.name=p1.name+i;
            System.out.println(p1.name);
            hashSet.add(p1);
        }
        System.out.println(hashSet.size());
        for (Object hashSet1:hashSet){
            if (hashSet1 instanceof Person){
                System.out.println("obj is persion");
            }
            System.out.println(hashSet1);
        }
    }

    @Test
    void test1(){
        if (1==2){
            System.out.println(1);
        }else if (2==2){
            System.out.println(2);
        }
        System.out.println("over");
    }

    @Test
    void contextLoads() {
       /* String d = "20200101";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date;
        try {
            date = sdf.parse(d);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        String[] s = {"20200101","SP2360","20201111"};
        for (String s1:s){
            try {
                Date date = dataParse(s1, "yyyyMMdd");
                System.out.println(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static Date dataParse(String dateString,String formatString)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        try {
            return sdf.parse(dateString);
        }catch (Exception e){
            return null;
        }
    }

}
class Person{
    public String name;
}