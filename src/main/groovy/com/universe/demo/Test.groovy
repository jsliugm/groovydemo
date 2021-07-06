package com.universe.demo

class Test {
     static addMethod(Class clazz, String methodName, Closure closure) {
        clazz.metaClass."$methodName" = closure
         clazz.metaClass.
         println clazz.metaClass."$methodName"
    }
    static print(){
        println "TEST"
    }
}