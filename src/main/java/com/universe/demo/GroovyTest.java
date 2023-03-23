package com.universe.demo;

import com.universe.demo.executor.CloneUtil;
import com.universe.demo.executor.GroovyExecutor;
import com.universe.demo.executor.ScriptPool;
import groovy.lang.*;
import org.junit.Test;

import javax.script.CompiledScript;
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
        //com.universe.demo.Test.addMethod(MyScript.class, name, closure);
    }

    public Closure compile(String scriptText) {
        GroovyShell groovyShell = new GroovyShell();
        return (Closure<?>) groovyShell.evaluate(scriptText);
    }

    @Test
    public void test() {
        String scriptText = "def xx = {if(it!=null&&it!=\"\"){it.length()}else{return null;}}";
        Map<String, Object> params = new HashMap<>();
        params.put("b", "中国人民解放军");
        params.put("c", 2);
        params.put("长度", compile(scriptText));
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
        executor.execute(scriptText, "param", "param");
        // executor.clearCache();
        scriptText = "def eval={ println 'b' };eval()";
        executor.execute(scriptText, "param", "param");

        scriptText = "def eval={ println 'c' };eval()";
        executor.execute(scriptText, "param", "param");
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

    class Param {
        Object policy;

        public Param(Object policy) {
            this.policy = policy;
        }

        public Object getPolicy() {
            return policy;
        }

        public void setPolicy(Object policy) {
            this.policy = policy;
        }
    }

    @Test
    public void testClosure() {
        Person person = new Person();
        person.setName("12345");

        String scriptText = "{it->println 'this in closure：'+this.person\nperson.name.length()+it.length()}";
        GroovyExecutor groovyExecutor = GroovyExecutor.getInstance();
        Object obj = groovyExecutor.execute(scriptText, "person", person);
        System.out.println(obj);
        Closure c = (Closure) obj;


        String st = "println 'this：'+this\n长度('123')";
        Map<String, Object> param = new HashMap<>();
        param.put("长度", obj);
        param.put("person", person);
        Object no = groovyExecutor.execute(st, param);
        System.out.println(no);

        while (true) {
            try {
                System.out.println("sleep 3000ms");
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testClosure2() {
        Person person = new Person();
        person.setName("12345");

        String scriptText = "{it->println 'this：'+this\nperson.name.length()+it.length()}";
        GroovyExecutor groovyExecutor = GroovyExecutor.getInstance();

        Object obj = groovyExecutor.execute(scriptText, "person", person);
        System.out.println(obj);

        String st = "长度('123')";
        Map<String, Object> param = new HashMap<>();
        param.put("长度", obj);
        param.put("person", person);
        Object no = groovyExecutor.execute(st, param);
        System.out.println(no);
    }

    GroovyExecutor groovyExecutor = GroovyExecutor.getInstance();

    @Test
    public void testClassLoad() {
        String scriptText = "{it->println 'this：'+this\nperson.name.length()+it.length()}";

        CompiledScript script1 = groovyExecutor.compiledScript(scriptText);


        scriptText = "{it->println 'this22222：'+this\nperson.name.length()+it.length()}";
        CompiledScript script2 = groovyExecutor.compiledScript(scriptText);

        System.out.println(script1 == script2);
    }

    @Test
    public void testReflect() {
        String scriptText = "person.mySchoolName";
        // Person.class.getMethod()
        Person p = new Person();
        p.setMySchoolName("家里蹲");
        Map<String, Object> param = new HashMap<>();
        param.put("person", p);
        Object o = groovyExecutor.execute(scriptText, param);
        Object o2 = groovyExecutor.execute(scriptText, param);

        System.out.println(o);
        System.out.println(o2);
    }

    @Test
    public void testReflect2() {
        String scriptText = "person.mySchoolName";
        // Person.class.getMethod()
        Person p = new Person();
        p.setMySchoolName("家里蹲");
        Map<String, Object> param = new HashMap<>();
        param.put("person", p);
        Object o = groovyExecutor.execute(scriptText, param);

        scriptText = "println person.mySchoolName";
        Object o2 = groovyExecutor.execute(scriptText, param);

        System.out.println(o);
        System.out.println(o2);


        scriptText = "println person.mySchoolName ";
        groovyExecutor.execute(scriptText, param);
    }

    @Test
    public void testCache() {
        String scriptText = "person.mySchoolName";
        // Person.class.getMethod()
        Person p = new Person();
        p.setMySchoolName("家里蹲");
        Map<String, Object> param = new HashMap<>();
        param.put("person", p);

        //compile
        GroovyShell groovyShell = new GroovyShell();
        Script script = groovyShell.parse(scriptText);
        Script script2 = groovyShell.parse(scriptText);
        //Script script3 = CloneUtil.clone(script2);
        //Script script2 = script.
        script.setBinding(new Binding(param));
        System.out.println(script.run());


    }


    @Test
    public void testCache2() {
        String scriptText = "person.mySchoolName";
        // Person.class.getMethod()
        Person p = new Person();
        p.setMySchoolName("家里蹲");
        Map<String, Object> param = new HashMap<>();
        param.put("person", p);
        ScriptPool scriptPool = new ScriptPool(scriptText);
        Script script = scriptPool.borrowObject();
        script.setBinding(new Binding(param));
        System.out.println(script.run());
    }


}
