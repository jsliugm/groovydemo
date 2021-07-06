package com.universe.groovy.jvm;

public class MyObject {
    @Override
    public String toString() {
        return "MyObject{}";
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize running");
    }
}
