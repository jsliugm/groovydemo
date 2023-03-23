package com.universe.groovy

import org.junit.Test

class StringTest {
    static void main(String[] args) {
        def str = '123456';
        println str[1..5]
    }

    @Test
    void test() {
        def str = "a11@bc"

        def rule = /(\w+)@(\w+)/;
        println str ==~ rule
        def m = str =~ rule
        if(m.find()) {
            println m.group(1)
        }
    }
    @Test
    void test23(){
        println null > 1
    }
    @Test
    void test5(){
        if("") println "xxxx"
        "".asBoolean()
    }
    @Test
    void test6(){
        def f = new File("D:/1.txt")
        if(f.exists()) {
            println new Date(f.lastModified())
        }
    }
}