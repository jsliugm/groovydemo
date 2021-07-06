package com.universe.demo;

import groovy.lang.DelegatingMetaClass;
import groovy.lang.MetaClass;

public class MyMetaClass extends DelegatingMetaClass {

    public MyMetaClass(MetaClass delegate) {
        super(delegate);
    }

    public MyMetaClass(Class theClass) {
        super(theClass);
    }

    public void test(){
        System.out.println("MyMetaClass");
    }
}
