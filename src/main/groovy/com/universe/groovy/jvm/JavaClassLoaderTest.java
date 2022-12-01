package com.universe.groovy.jvm;

public class JavaClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader loader = JavaClassLoaderTest.class.getClassLoader();
        while (loader != null) {
            System.out.println(loader);
            loader = loader.getParent();
        }
        System.out.println(loader);
    }
}
