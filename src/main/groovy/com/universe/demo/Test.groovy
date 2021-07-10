package com.universe.demo

class Test {
     static addMethod(Class clazz, String methodName, Closure closure) {
        clazz.metaClass["myClosureMap"] = {methodName:closure}
    }
    static print(){
        println "TEST"
    }
}