package com.universe.groovy

class WeiPaiTest {
    private void run() {
        println this
        println "hello world!!!"
    }
    static void main(String[] args) {
        //引用
        println '引用........'
        def run = new WeiPaiTest().&run
        println run
        //run ..
        println 'run........'
        run()
        //委派
        println '委派........'
        Hello.metaClass.run = new WeiPaiTest().&run
        Hello hello = new Hello();
        hello.run()
    }
}
class Hello {
}