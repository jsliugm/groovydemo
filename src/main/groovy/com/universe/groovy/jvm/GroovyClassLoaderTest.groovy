package com.universe.groovy.jvm

import groovyjarjarasm.asm.ClassVisitor
import org.junit.Test

class GroovyClassLoaderTest {
    @Test
    void test() {
        printClassLoader GroovyClassLoaderTest.class
    }

    private static void printClassLoader(Class clazz){
        println "开始打印classloader"
        ClassLoader classLoader = clazz.getClassLoader()
        while (classLoader) {
            println classLoader
            classLoader = classLoader.getParent()
        }
        println classLoader
        println "打印classloader结束^V^"

    }
    static void main(String[] args) {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader()
        Class clazz = groovyClassLoader.parseClass "def xx = {println 'xxx'} \r\n xx()"
        printClassLoader clazz
        clazz = groovyClassLoader.parseClass "def xx = {println 'xxx'} \r\n xx()"
        printClassLoader clazz
    }
    @Test
    void test30(){
        println this.class.classLoader
        println GroovyClassLoaderTest.classLoader
        println()

        println groovy.ui.GroovyMain.classLoader
        println ClassVisitor.classLoader
        println()

        println String.classLoader
        println()

        println org.codehaus.groovy.tools.GroovyStarter.classLoader
        println ClassLoader.systemClassLoader.findLoadedClass('org.codehaus.groovy.tools.GroovyStarter')?.classLoader
        println()
    }
}
