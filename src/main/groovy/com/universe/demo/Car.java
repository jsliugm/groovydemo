package com.universe.demo;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;

public class Car implements GroovyObject {
    @Override
    public Object invokeMethod(String name, Object args) {
        return null;
    }

    @Override
    public Object getProperty(String propertyName) {
        return null;
    }

    @Override
    public void setProperty(String propertyName, Object newValue) {

    }

    @Override
    public MetaClass getMetaClass() {
        return null;
    }

    @Override
    public void setMetaClass(MetaClass metaClass) {

    }
}
