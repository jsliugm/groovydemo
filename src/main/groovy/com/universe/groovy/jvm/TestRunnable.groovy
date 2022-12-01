package com.universe.groovy.jvm

import java.lang.ref.ReferenceQueue

class TestRunnable implements Runnable {
    private ReferenceQueue<MyObject> referenceQueue;

    TestRunnable(ReferenceQueue<MyObject> referenceQueue) {
        this.referenceQueue = referenceQueue
    }

    @Override
    void run() {
        println "xxxxx "+referenceQueue.remove().get()
    }
}
