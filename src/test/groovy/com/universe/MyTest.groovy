package com.universe

import org.junit.Test

class MyTest {

    @Test
    void test(){
        print {println "xxxxxxxx"}
    }


     void print(a) {
         a()
     }

    @Test
    void test2(){
        print2 "xxxxxx"
    }
    void print2(String xxx) {
        println xxx
    }
    @Test
    void testBig(){
        List<BigDecimal> list = new ArrayList<>();
        list.add(100)
        list.add(1000)
        StringJoiner sj = new StringJoiner(",")

    }
}