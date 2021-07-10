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
        System.out.println("closure name = "+closure.getClass().getName());
        com.universe.demo.Test.addMethod(Script.class, name, closure);
    }

    @Test
    public void test() {
        String scriptText = "def xx = {if(it!=null&&it!=\"\"){ println '**length begin'; println owner;println this;println delegate;println 'length end**';it.length() }else{return null;}}";
        compileClosureScript(scriptText,"长度");

        Map<String, Object> params = new HashMap<>();
        params.put("b", "中国人民解放军");
        params.put("c", 2);
        String runScript =" def cal(){ return {println 长度(b);println this.respondsTo('长度');println owner;println delegate;}()};\r\ncal();";
        GroovyExecutor.invoke(runScript, params);

        runScript =" def cal(){ return {println 长度(b) ;println this.respondsTo('长度');println owner;println delegate;}()};\r\ncal();";
        //scriptText = "def xx = {if(it!=null&&it!=\"\"){return 22222}else{return null;}}";
        //compileClosureScript(scriptText,"长度");

        GroovyExecutor.invoke(runScript, params);

    }

    @Test
    public void test2() {
        String scriptText = "def xx= {println 'a'}";
        compileClosureScript( scriptText,"长度");
        //Map<String, Object> params = new HashMap<>();
        //GroovyExecutor.invoke(" def cal(){ test()};\r\ncal();", params);
        Map<String, Object> params = new HashMap<>();
        params.put("b", "中国人民解放军");
        params.put("c", 2);
        GroovyExecutor.invoke("def cal(){ 长度()};\r\ncal();", params);

        scriptText = "def xx= {println 'b'}";
        compileClosureScript( scriptText,"长度");
        GroovyExecutor.invoke("def cal(){ 长度()};\r\ncal();", params);
    }
}
