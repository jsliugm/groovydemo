package com.universe.groovy

class ListTest {
    static void main(String[] args) {
        def l = [1, 2, 3, 4]

        def x = l.findAll { it == 5 }

        println x.size()
    }
}