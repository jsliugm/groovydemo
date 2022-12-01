package com.universe.groovy.mop

class A {
    void test() {
        println 'test'
    }
    @Override
    Object invokeMethod(String name, Object args) {
        println 'invokeMethod'
        return "invokeMethod"
    }

    static void main(String[] args) {
        A a = new A()
        a.test()
        a.test2()
    }
}
