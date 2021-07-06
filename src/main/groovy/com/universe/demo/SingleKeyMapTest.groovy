package com.universe.demo

import org.codehaus.groovy.util.SingleKeyHashMap

class SingleKeyMapTest {
    static void main(String[] args) {
        testFirstWriteThenRead()
    }

    private static void testFirstWriteThenRead() {
        Map<String, String> hashMap2 = new HashMap();
        addValue(hashMap2);
        getValue(hashMap2)

        Map<String, String> hashMap = new HashMap();
        addValue(hashMap);
        getValue(hashMap)

        SingleKeyHashMap<String, String> singleKeyHashMap = new SingleKeyHashMap();
        addValue(singleKeyHashMap);
        getValue(singleKeyHashMap)

    }

    final static int count=1000

    private static void getValue(def map) {
        long time = System.currentTimeMillis()
        count.times {
            map.get("key$it");
        }
        println (System.currentTimeMillis()-time)
    }

    private static void addValue(def map) {
        long time = System.currentTimeMillis()
        count.times {
            map.put("key$it", "value$it");
        }
        println (System.currentTimeMillis()-time)
    }
}
