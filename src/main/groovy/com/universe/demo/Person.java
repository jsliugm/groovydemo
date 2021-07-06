package com.universe.demo;

public class Person {
    private String name;
    private int age;

    static {
        System.out.println("xxx");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
