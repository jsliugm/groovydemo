package com.universe.groovy

class ClosureTest {

    def zz = {
        println delegate
        println owner
        println this
        println "\r\njj............."
        def jj = {
            println delegate
            println owner
            println this
        }
        jj()
    }

    static void main(String[] args) {
        def xx = {
            println "xx..........."
            println delegate
            println owner
            println this
            println "yy..........."
            def yy = {
                println delegate
                println owner
                println this
            }
            yy()
        }
        println "xx = ${xx.toString()}"
        xx()
        //
        println "\r\nzz..............."
        ClosureTest closureTest = new ClosureTest()
        closureTest.zz()
    }
}