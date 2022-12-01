package com.universe.demo

import java.util.concurrent.ConcurrentHashMap


abstract class MyScript extends Script {
    static Map<String, Closure> factorMap = new ConcurrentHashMap<>()

    void test() {
        "MyScript"
    }


    @Override
    Object invokeMethod(String name, Object args) {
        if(name.startsWith("factor_")) {
            Closure closure = factorMap.get(name)
            return closure.call(args)
        }
        return super.invokeMethod(name, args)
    }

    static void addClosure(String name ,String closureText){
        GroovyShell groovyShell = new GroovyShell();
        Closure closure = (Closure<?>) groovyShell.evaluate(closureText);
        factorMap.put(name,closure)
    }
}