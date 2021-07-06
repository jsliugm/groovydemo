package com.universe.demo;

import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class GroovyTest {
    public static void main(String[] args) {
        GroovyClassLoader classLoader = new GroovyClassLoader();
        Class groovyClass = classLoader.parseClass("def cal(int a, int b){\n" +
                "    return {a+b}()\n" +
                "}");
        GroovyExecutor groovyExecutor = GroovyExecutor.getInstance();
        Object obj = groovyExecutor.execute("def cal(int a, int b){\n" +
                "    return {a+b}()\n" +
                "}", "xx", "xx");
        System.out.println(obj);
    }

    public static void compileClosureScript(String scriptText,String name){
        GroovyShell groovyShell = new GroovyShell();
        Closure closure = (Closure<?>) groovyShell.evaluate(scriptText);
        com.universe.demo.Test.addMethod(Script.class, name, closure);
    }

    @Test
    public void test() {
        String scriptText = "def xx = {if(it!=null&&it!=\"\"){println delegate;return 1111}else{return null;}}";
        compileClosureScript(scriptText,"长度");

        Map<String, Object> params = new HashMap<>();
        params.put("b", "中国人民解放军");
        params.put("c", 2);
        System.out.println(GroovyExecutor.invoke(" def cal(){ return {长度(b)}()};\r\ncal();", params));

        scriptText = "def xx = {if(it!=null&&it!=\"\"){println delegate;return 22222}else{return null;}}";
        compileClosureScript(scriptText,"长度");
        System.out.println(GroovyExecutor.invoke(" def cal(){  return {长度(b)}()};\r\ncal();", params));

    }

    @Test
    public void test2() {
        String scriptText = "def xx={it}";
        GroovyShell groovyShell = new GroovyShell();
        Closure closure = (Closure<?>) groovyShell.evaluate(scriptText);
        com.universe.demo.Test.addMethod(MyScript.class, "长度", closure);
        //Map<String, Object> params = new HashMap<>();
        //GroovyExecutor.invoke(" def cal(){ test()};\r\ncal();", params);
        Map<String, Object> params = new HashMap<>();
        params.put("b", "中国人民解放军");
        params.put("c", 2);
        GroovyExecutor.invoke("def cal(){ 长度()};\r\ncal();", params);
    }
}
