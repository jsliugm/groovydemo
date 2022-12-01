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
}