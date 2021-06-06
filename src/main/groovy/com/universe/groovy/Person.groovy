package com.universe.groovy

trait Person{
    void printAge(){
        println "personAge"
    }
}
class Student implements Person {
    void printAge(){
        println "studentAge"
    }
}
class Main{
     static void main(String[] args) {
       println  null<0

         new Student().printAge()
    }
}