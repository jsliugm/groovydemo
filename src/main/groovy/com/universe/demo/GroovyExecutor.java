package com.universe.demo;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.util.ManagedConcurrentValueMap;
import org.codehaus.groovy.util.ReferenceBundle;

import javax.script.*;
import java.security.MessageDigest;
import java.util.Map;

public class GroovyExecutor {
    private ScriptEngine scriptEngine;
    private static GroovyExecutor groovyExecutor = new GroovyExecutor();

    public static GroovyExecutor getInstance() {
        return groovyExecutor;
    }

    private GroovyExecutor() {
    }

    private ScriptEngine getScriptEngine() {
        if (this.scriptEngine == null) {
            ScriptEngineManager factory = new ScriptEngineManager();
            this.scriptEngine = factory.getEngineByName("groovy");
        }
        return this.scriptEngine;
    }

    public Object execute(String script, String objectTypeName, Object param) {
        ScriptEngine scriptEngine = this.getScriptEngine();
        Compilable compilable = (Compilable) scriptEngine;
        try {
            CompiledScript compiledScript = compilable.compile(script);
            Bindings bindings = scriptEngine.createBindings();
            bindings.put(objectTypeName, param);
            return compiledScript.eval(bindings);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final static ManagedConcurrentValueMap<String, Class<Script>> zlassMaps = new ManagedConcurrentValueMap<>(ReferenceBundle.getSoftBundle());

    public Class<Script> compile(String scriptText) {
        String key = fingerKey(scriptText);
        Class<Script> script = zlassMaps.get(key);
        if (script == null) {
            synchronized (key.intern()) {
                // Double Check
                script = zlassMaps.get(key);
                if (script == null) {
                    GroovyClassLoader classLoader = new GroovyClassLoader();
                    script = classLoader.parseClass(scriptText);
                    zlassMaps.put(key, script);
                    return script;
                }
            }
        }
        return null;
    }

    public static Object execute(Class<Script> scriptClass, String paramName, Object param) {
        Binding binding = new Binding();
        binding.setVariable(paramName, param);
        Script scriptObj = InvokerHelper.createScript(scriptClass, binding);
        return scriptObj.run();
    }

    public static Object invoke(String scriptText, Map<String, Object> params) {
        String key = fingerKey(scriptText);
        Class<Script> script = zlassMaps.get(key);
        if (script == null) {
            synchronized (key.intern()) {
                // Double Check
                script = zlassMaps.get(key);
                if (script == null) {
                    GroovyClassLoader classLoader = new GroovyClassLoader();
//                    CompilerConfiguration config = new CompilerConfiguration();
//                    config.setSourceEncoding("UTF-8");
//                    config.setScriptBaseClass("com.universe.demo.MyScript");
//                    GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(),config);
                    script = classLoader.parseClass(scriptText);
                    zlassMaps.put(key, script);
                }
            }
        }
        Binding binding = new Binding();
        for (Map.Entry<String, Object> ent : params.entrySet()) {
            binding.setVariable(ent.getKey(), ent.getValue());
        }
        Script scriptObj = InvokerHelper.createScript(script, binding);
        return scriptObj.run();
    }

    // 为脚本代码生成md5指纹
    public static String fingerKey(String scriptText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(scriptText.getBytes("utf-8"));

            final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
            StringBuilder ret = new StringBuilder(bytes.length * 2);
            for (int i = 0; i < bytes.length; i++) {
                ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
                ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
            }
            return ret.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
