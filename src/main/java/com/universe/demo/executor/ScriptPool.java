package com.universe.demo.executor;

import groovy.lang.Script;

import java.util.concurrent.LinkedBlockingQueue;

public class ScriptPool {
    private LinkedBlockingQueue<Script> pool;
    private int max = 5;

    private String script;

    public ScriptPool() {
    }

    public ScriptPool(String script) {
        this.script = script;
        init();
    }

    public ScriptPool(String script, int max) {
        this.script = script;
        this.max = max;
        pool = new LinkedBlockingQueue<>(max);
        init();
    }


    private void init() {
        for (int i = 0; i < max; i++) {
            try {
                pool.put(createObject());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Script borrowObject() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void returnObject(Script object) {
        try {
            pool.put(object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Script createObject() {
        // create object
        return GroovyExecutor3.getInstance().getScript(script);
    }
}
