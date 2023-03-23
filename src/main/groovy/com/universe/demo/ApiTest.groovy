package com.universe.demo

import org.junit.Test

import java.util.stream.Collectors

class ApiTest {
    @Test
    void testStringJoin() {
        List l = ['1', '2', '3']
        println l.join(",")
        println l.stream().map({ it -> "'$it'" }).collect(Collectors.joining(","))
    }

    @Test
    void testSubstring() {
        def x = "1234"
        println x.substring(3-1,4)
    }
}
