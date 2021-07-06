package com.universe.demo;

import org.codehaus.groovy.util.ManagedConcurrentValueMap;
import org.codehaus.groovy.util.ReferenceBundle;

public class MapTest {
    private final static ManagedConcurrentValueMap<String, byte[]> globalClosures = new ManagedConcurrentValueMap<>(ReferenceBundle.getSoftBundle());
    static byte[] ref;
    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 300000000; i++) {
                if(i==0) {
                    ref  = new byte[1024];
                   globalClosures.put("abc" + i, ref);
                    //globalClosures.put("abc" + i, new byte[1024]);
                }else {
                    globalClosures.put("abc" + i, new byte[1024]);
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(globalClosures.get("abc0"));
            }
        }).start();

    }
}
