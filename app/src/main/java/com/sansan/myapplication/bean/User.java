package com.sansan.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class User {
    //省略其它
     @Expose public String name;
    @Expose public int age;
    @Expose public boolean sex;
    @Expose public static String emailAddress;

    public User(String name, int age) {
        this.name=name;
        this.age=age;
    }

    public User(String name, int age,String email) {
        this.name=name;
        this.age=age;
        emailAddress=email;
    }

    public User(String name, int age,boolean sex,String email) {
        this(name,age,email);
        this.sex=sex;
    }
    @Override
    public String toString() {
        return "name="+name+" age="+age+" email="+emailAddress;
    }
}
