package com.universe.demo.executor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import groovy.lang.Script;

import java.util.concurrent.TimeUnit;

public class ScriptCache {
    private final static ThreadLocal<Cache<String, Script>> cacheThreadLocal = new ThreadLocal<>();

    public static Cache<String, Script> getCache() {
        Cache<String, Script> cache = cacheThreadLocal.get();
        if (cache == null) {
            synchronized (Thread.currentThread()) {
                cache = cacheThreadLocal.get();
                if (cache == null) {
                    cache = CacheBuilder.newBuilder()
                            .maximumSize(5000)
                            .expireAfterWrite(10, TimeUnit.MINUTES)
//                            .removalListener(notification ->
//                                    System.out.println("Object " + notification.getKey() + " removed from cache"))
                            .build();
                    cacheThreadLocal.set(cache);
                }
            }
        }
        return cache;
    }

    public static Script get(String key) {
        // 从缓存中获取数据
        Script script = getCache().getIfPresent(key);
        if (script == null) {
            script = GroovyExecutor3.getInstance().getScript(key);
            put(key, script);
        }
        return script;
    }

    public static void put(String key, Script value) {
        // 将数据放入缓存中
        getCache().put(key, value);
    }

    public static void remove(String key) {
        // 从缓存中移除数据
        getCache().invalidate(key);
    }

}
