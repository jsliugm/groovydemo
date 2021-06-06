package com.universe.groovy

class DoSomething {
    static void main(String[] args) {
        def tt = new Cat()
        def it = 'running'
        tt."${it}"()
        def params = ["hot dog"]
        tt."eating"(*params)
    }
}

class Cat {
    void running() {
        println "cat running"
    }
    void eating(String foot){
        println "cat eating ${foot}"
    }
}

class dog {
    void running() {
        println "dog running"
    }
    void eating(String foot){
        println "dog eating ${foot}"
    }
}