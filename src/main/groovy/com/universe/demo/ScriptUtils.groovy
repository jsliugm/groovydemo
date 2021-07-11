package com.universe.demo

class ScriptUtils {
    static addMethod(Class clazz,String name,Closure closure){
        clazz.metaClass."$name" = closure
    }
}
