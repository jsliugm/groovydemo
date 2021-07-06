package com.universe.demo;

import groovy.lang.Closure;
import groovy.lang.Script;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class GroovyExecutor {
    private static ScriptEngine getScripEngine() {
        return null;
    }

    public static void main(String[] args) throws Exception {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");
        String closureString = "def getFactor_1 = {a==1}";
        Object obj = engine.eval(closureString);
        ScriptUtils.addMethod(Script.class, "getFactor_1", (Closure) obj);

        String script = "factor_1 && 1==1";
//
//        Bindings binding = engine.createBindings();
//        binding.put("a", 1);
//        obj = engine.eval("def eval ={a==2 && 1==1}\r\neval()", binding);
//        System.out.println(obj);
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(
                    () -> {
                        while(true){
                            Bindings binding = engine.createBindings();
                            binding.put("a", finalI);
                            Object obj1 = null;
                            try {
                                obj1 = engine.eval("def eval ={factor_1}\r\neval()", binding);
                            } catch (ScriptException e) {
                                e.printStackTrace();
                            }
                            System.out.printf("%s %s\n",Thread.currentThread().getName(),obj1);
                        }
                    }
            ).start();
        }


//        String fact = "def factorial(n, msg) { println(msg);return n == 1 ? 1 : n * factorial(n - 1, msg);}";
//        engine.eval(fact);
//        Invocable inv = (Invocable) engine;
//        Object[] params = {new Integer(5), "ssss"};
//        Object result = inv.invokeFunction("factorial", params);
//        System.out.println(result);
//
//        Bindings binding = engine.createBindings();
//        binding.put("date", new Date());
//        engine.eval("def getTime(){return date.getTime();}", binding);
//        engine.eval("def sayHello(name,age){return 'Hello,I am ' + name + ',age ' + age;}");
//        Object time = inv.invokeFunction("getTime", null);
//        System.out.println((Long) time);
//        String message = (String) inv.invokeFunction("sayHello", "zhangsan", new Integer(12));
//        System.out.println(message);
    }
}
