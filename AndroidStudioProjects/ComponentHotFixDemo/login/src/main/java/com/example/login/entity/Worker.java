package com.example.login.entity;

/**
 * Author : ZSX
 * Date : 2019-11-29
 * Description :
 */
public class Worker {

    private String name = "少年";

    private int age = 18;

    public String singer = "许巍";

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int pAge) {
        age = pAge;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String pSinger) {
        singer = pSinger;
    }
}
