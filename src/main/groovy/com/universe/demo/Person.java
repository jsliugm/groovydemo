package com.universe.demo;

import groovy.lang.Closure;

public class Person {
    private String name;
    private int age;

    static {
        System.out.println("Person静态方法块");
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

    public Person test(Closure closure){
        closure = closure.rehydrate(this,this,this);
        closure.run();
        return this;
    }
}
