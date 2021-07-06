package com.universe.demo
class SomeGroovyClass{
    def invokeMethod(String name, Object args) {
         "call invokeMethod $name $args"
    }
    def test(){
        'method exists'
    }

    def methodMissing(String name, def args) {
        'method missing'
    }

    def name='ha'
    @Override
    Object getProperty(String propertyName) {
        if(propertyName == 'name') {return name}
        "Hello World!!!"
    }

    static void main(String[] args) {
//        def someGroovyClass = new SomeGroovyClass()
//        println(someGroovyClass.test())
//        println(someGroovyClass.someMethod())
//        println someGroovyClass.name

        Person p = new Person()
     //   println p.name
//        Student student = new Student()
//        println student.name
        println p.getName()
    }
}
