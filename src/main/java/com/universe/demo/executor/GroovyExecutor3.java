package com.universe.demo.executor;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.util.ManagedConcurrentValueMap;
import org.codehaus.groovy.util.ReferenceBundle;

import java.security.MessageDigest;
import java.util.Map;

/**
 * 实现脚本对象池
 */
public class GroovyExecutor3 {
    private final static ManagedConcurrentValueMap<String, Class<Script>> zlassMaps = new ManagedConcurrentValueMap<>(ReferenceBundle.getSoftBundle());
    private final static GroovyClassLoader classLoader = new GroovyClassLoader();
    private static GroovyExecutor3 groovyExecutor = new GroovyExecutor3();
    private final static Binding binding = new Binding();

    public static GroovyExecutor3 getInstance() {
        return groovyExecutor;
    }

    private GroovyExecutor3() {
    }

    public Class<Script> compile(String scriptText) {
        String key = fingerKey(scriptText);
        Class<Script> script = zlassMaps.get(key);
        if (script == null) {
            synchronized (key.intern()) {
                // Double Check
                script = zlassMaps.get(key);
                if (script == null) {
                    // GroovyClassLoader classLoader = new GroovyClassLoader();
                    script = classLoader.parseClass(scriptText);
                    zlassMaps.put(key, script);
                    return script;
                }
            }
        }
        return script;
    }

    public  Object execute(Class<Script> scriptClass, String paramName, Object param) {
        Binding binding = new Binding();
        binding.setVariable(paramName, param);
        Script scriptObj = InvokerHelper.createScript(scriptClass, binding);
        return scriptObj.run();
    }

    public  Object invoke(String scriptText, Map<String, Object> params) {
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


    public Script getScript(String script) {
        Class<Script> scriptClass = compile(script);
        if (scriptClass == null) {
            return null;
        }
        return InvokerHelper.createScript(scriptClass, binding);
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
