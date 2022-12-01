package com.universe.demo;

import com.universe.demo.executor.GroovyExecutor;
import com.universe.demo.executor.GroovyExecutor2;
import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
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

    public static void compileClosureScript(String scriptText, String name) {
        GroovyShell groovyShell = new GroovyShell();
        Closure closure = (Closure<?>) groovyShell.evaluate(scriptText);
        //System.out.println("closure name = "+closure.getClass().getName());
        com.universe.demo.Test.addMethod(MyScript.class, name, closure);
    }

    public Closure compile(String scriptText){
        GroovyShell groovyShell = new GroovyShell();
        return  (Closure<?>) groovyShell.evaluate(scriptText);
    }

    @Test
    public void test() {
        String scriptText = "def xx = {if(it!=null&&it!=\"\"){it.length()}else{return null;}}";
        Map<String, Object> params = new HashMap<>();
        params.put("b", "中国人民解放军");
        params.put("c", 2);
        params.put("长度",compile(scriptText));
        String runScript = " def cal(){ return {println 长度(b);}()};\r\ncal();";
        GroovyExecutor.invoke(runScript, params);
    }

    @Test
    public void test2() {
        String scriptText = "def xx= {println 'a'}";
        compileClosureScript(scriptText, "长度");
        //Map<String, Object> params = new HashMap<>();
        //GroovyExecutor.invoke(" def cal(){ test()};\r\ncal();", params);
        Map<String, Object> params = new HashMap<>();
        params.put("b", "中国人民解放军");
        params.put("c", 2);
        GroovyExecutor.invoke("def cal(){ 长度()};\r\ncal();", params);

        scriptText = "def xx= {println 'b'}";
        compileClosureScript(scriptText, "长度");
        GroovyExecutor.invoke("def cal(){ 长度()};\r\ncal();", params);
    }

    @Test
    public void test3() {
        String scriptText = "def xx= {param1,param2->println param1}";
        MyScript.addClosure("factor_1", scriptText);
        //Map<String, Object> params = new HashMap<>();
        //GroovyExecutor.invoke(" def cal(){ test()};\r\ncal();", params);
        Map<String, Object> params = new HashMap<>();
        params.put("b", "中国人民解放军");
        params.put("c", 2);
        GroovyExecutor.invoke("def cal(){ factorMap['factor_1']('hello','cc')};\r\ncal();", params);

        //scriptText = "def xx= {param->println param + 1}";
       // MyScript.addClosure("factor_1", scriptText);
       // Object result = GroovyExecutor.invoke("def cal(){ factor_1('abc')};\r\ncal();", params);
      //  System.out.println(result);

        //scriptText = "def xx= {println 'b'}";
        //compileClosureScript( scriptText,"长度");
        // GroovyExecutor.invoke("def cal(){ 长度()};\r\ncal();", params);
    }

    @Test
    public void test86() {
        String scriptText = "def eval={ println 'a' };eval()";
        GroovyExecutor executor = GroovyExecutor.getInstance();
        executor.execute(scriptText,"param","param");
       // executor.clearCache();
        scriptText = "def eval={ println 'b' };eval()";
        executor.execute(scriptText,"param","param");

        scriptText = "def eval={ println 'c' };eval()";
        executor.execute(scriptText,"param","param");
//        GroovyExecutor2 executor2 = GroovyExecutor2.getInstance();
//        scriptText = "def eval={ println 'b' };eval()";
//        executor2.execute(scriptText,"param","param");
    }


    @Test
    public void test104() {
        Person person = new Person();
        person.setAge(101);
       // String scriptText = "def eval={ person.age };eval(person)";
        //String scriptText = "import java.util.*\n person.age";
        String scriptText = "import java.util.*\n({person.age}(person))";
        GroovyExecutor executor = GroovyExecutor.getInstance();
        System.out.println(executor.execute(scriptText, "person", person));
    }

}
