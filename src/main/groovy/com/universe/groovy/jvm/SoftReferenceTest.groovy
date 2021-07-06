package com.universe.groovy.jvm

import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

class SoftReferenceTest {
    static void testSoft(){
        Student student = new Student()
        16.times {
            println 'times---'+it
            student.name='abc'
        }
        student = null

        MyObject myObject = new MyObject();

        ReferenceQueue<MyObject> queue = new ReferenceQueue<>();

        SoftReference<MyObject> softReference = new SoftReference<>(myObject, queue);

        new Thread(new TestRunnable(queue)).start();

        myObject = null

        byte[] bytes = new byte[4*1024*1024]
    }

    static void testWeak(){
        Student student = new Student()
        16.times {
            println 'times---'+it
            student.name='abc'
        }
        student = null

        MyObject myObject = new MyObject();

        ReferenceQueue<MyObject> queue = new ReferenceQueue<>();

        WeakReference<MyObject> softReference = new WeakReference<>(myObject, queue);

        new Thread(new TestRunnable(queue)).start();

        myObject = null

        byte[] bytes = new byte[4*1024*1024]
    }
    static void main(String[] args) {
       testWeak()
    }
}