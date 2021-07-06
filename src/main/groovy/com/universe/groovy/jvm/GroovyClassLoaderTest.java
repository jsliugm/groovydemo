package com.universe.groovy.jvm;

import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.codehaus.groovy.util.ManagedConcurrentValueMap;
import org.codehaus.groovy.util.ReferenceBundle;

import javax.script.ScriptException;

public class GroovyClassLoaderTest {
    private static void test() {
        //GroovyScriptEngineImpl se = new GroovyScriptEngineImpl(new GroovyClassLoader());
        ManagedConcurrentValueMap<String, Class<Script>> map = new ManagedConcurrentValueMap<>(ReferenceBundle.getSoftBundle());
//        GroovyClassLoader classLoader = new GroovyClassLoader();
        int i = 0;
        while (true) {
            String scriptString = "println(\"hello$i\")";
            Class<Script> scriptClass = new GroovyClassLoader().parseClass(scriptString);
            map.put(scriptString, scriptClass);
            i++;
        }
    }

    private static void testClassloader() {
        GroovyScriptEngineImpl se;
        GroovyClassLoader classLoader = new GroovyClassLoader();
        while (true) {
            //classLoader.parseClass("println(\"hello\")");
            new GroovyClassLoader().parseClass("println(\"hello\")");
            //se = new GroovyScriptEngineImpl(new GroovyClassLoader());
            //CompiledScript script = se.compile("println(\"hello\")");
            //script.eval(se.createBindings());
        }
    }

    public static void main(String[] args) throws ScriptException {
        test();
    }
}
