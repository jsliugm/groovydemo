package com.universe.demo

class Test {
     static addMethod(Class clazz, String methodName, Closure closure) {
        clazz.metaClass["myClosureMap"] = {methodName:closure}
    }
     static void main(String[] args) {
         BigDecimal d1 =2
         BigDecimal d2 = 1
        def x = d1-d2
         println x
    }
}