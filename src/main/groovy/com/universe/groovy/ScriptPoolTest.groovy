package com.universe.groovy

import com.google.common.cache.Cache
import com.universe.demo.Person
import com.universe.demo.executor.GroovyExecutor3
import com.universe.demo.executor.ScriptCache
import com.universe.demo.executor.ScriptPool

import java.util.concurrent.atomic.AtomicInteger

/**
 *
 *
 * -XX:+UnlockCommercialFeatures
 -XX:+FlightRecorder
 -XX:StartFlightRecording=duration=60s,filename=myrecordingTool.jfr
 *
 */
class ScriptPoolTest {

    static void main(String[] args) {
        def nThread = 10
        def maxExec = 5000
        int ruleCount = 5000

        testCache(nThread, maxExec, ruleCount)
        testNormal(nThread, maxExec, ruleCount)


        println "=============================="
        maxExec = 100000
        testNormal(nThread, maxExec, ruleCount)
        testCache(nThread, maxExec, ruleCount)


    }
    /**
     * 生成100个groovy脚本
     * 随机调用
     */
    private static void testTotal() {
        def nThread = 10
        def maxExec = 100000

        def counter1 = []
        def counter2 = []


        nThread.times {
            counter1 << 0
            counter2 << 0
        }

        AtomicInteger overFlag1 = new AtomicInteger(0)
        AtomicInteger overFlag2 = new AtomicInteger(0)


        int ruleCount = 5000
        String scriptText = "person.mySchoolName + "
        def scriptList = []
        ruleCount.times {
            scriptList << scriptText + it
        }
        //编译ScriptPool
        def scriptPools = []
        scriptList.each {
            scriptPools << new ScriptPool(it, nThread)
        }

        Person p = new Person()
        p.setMySchoolName("家里蹲")
        Map<String, Object> param = new HashMap<>()
        param.put("person", p)

//pool
        nThread.times { out ->
            Thread.start {
                Random random = new Random()
                long begin = System.currentTimeMillis()
                maxExec.times {
                    ScriptPool scriptPool = scriptPools[random.nextInt(ruleCount)]
                    Script script = scriptPool.borrowObject()
                    try {
                        script.setBinding(new Binding(param))
                        println script.run()
                    } finally {
                        scriptPool.returnObject(script)
                    }
                }
                //println " pool 耗时 ： ${(System.currentTimeMillis() - begin)}"
                counter1.set(out, counter1.get(out) + System.currentTimeMillis() - begin)
                overFlag1.getAndIncrement()
            }
        }
//normal
        nThread.times { out ->
            Thread.start {
                Random random = new Random()
                long begin = System.currentTimeMillis()
                maxExec.times {
                    def st = scriptList[random.nextInt(ruleCount)]
                    //println(st)
                    GroovyExecutor3.getInstance().invoke(st, param)
                }
                //println " 普通 耗时 ： ${(System.currentTimeMillis() - begin)}"
                counter2.set(out, counter2.get(out) + System.currentTimeMillis() - begin)
                overFlag2.getAndIncrement()
            }
        }

        while (overFlag1.get() < 5 || overFlag2.get() < 5) {
            sleep(100)
        }

        println " pool total ${counter1.sum()}"
        println " normal total ${counter2.sum()}"

    }

    private static void testNormal(nThread, maxExec, ruleCount) {

        def counter2 = []

        nThread.times {
            counter2 << 0
        }


        AtomicInteger overFlag2 = new AtomicInteger(0)


        String scriptText = "person.mySchoolName + "
        def scriptList = []
        ruleCount.times {
            scriptList << scriptText + it
        }


        Person p = new Person()
        p.setMySchoolName("家里蹲")
        Map<String, Object> param = new HashMap<>()
        param.put("person", p)

//normal
        nThread.times { out ->
            Thread.start {
                Random random = new Random()
                long begin = System.currentTimeMillis()
                maxExec.times {
                    def st = scriptList[random.nextInt(ruleCount)]
                    //println(st)
                    GroovyExecutor3.getInstance().invoke(st, param)
                }
                //println " 普通 耗时 ： ${(System.currentTimeMillis() - begin)}"
                counter2.set(out, counter2.get(out) + System.currentTimeMillis() - begin)
                overFlag2.getAndIncrement()
            }
        }

        while (overFlag2.get() < 5) {
            sleep(100)
        }


        println " normal total ${counter2.sum()}"

    }

    private static void testCache(nThread, maxExec, ruleCount) {
        def counter1 = []
        //   def counter2 = []


        nThread.times {
            counter1 << 0
            //counter2 << 0
        }

        AtomicInteger overFlag1 = new AtomicInteger(0)
        // AtomicInteger overFlag2 = new AtomicInteger(0)


        String scriptText = "person.mySchoolName + "
        def scriptList = []
        ruleCount.times {
            scriptList << scriptText + it
        }
        //编译ScriptPool
        //def scriptPools = []

        Person p = new Person()
        p.setMySchoolName("家里蹲")
        Map<String, Object> param = new HashMap<>()
        param.put("person", p)

//pool
        nThread.times { out ->
            Thread.start {
                Random random = new Random()
                long begin = System.currentTimeMillis()
                maxExec.times {
                    Script script = ScriptCache.get(scriptList[random.nextInt(ruleCount)])
                    script.setBinding(new Binding(param))
                    script.run()
                }
                //println " pool 耗时 ： ${(System.currentTimeMillis() - begin)}"
                counter1.set(out, counter1.get(out) + System.currentTimeMillis() - begin)
                overFlag1.getAndIncrement()
            }
        }
        while (overFlag1.get() < 5) {
            sleep(100)
        }
        println " pool total ${counter1.sum()}"
    }

    private static void testPool() {
        def nThread = 10
        def maxExec = Integer.MAX_VALUE

        def counter1 = []
        //   def counter2 = []


        nThread.times {
            counter1 << 0
            //counter2 << 0
        }

        AtomicInteger overFlag1 = new AtomicInteger(0)
        // AtomicInteger overFlag2 = new AtomicInteger(0)

        int ruleCount = 5000
        String scriptText = "person.mySchoolName + "
        def scriptList = []
        ruleCount.times {
            scriptList << scriptText + it
        }
        //编译ScriptPool
        def scriptPools = []
        scriptList.each {
            scriptPools << new ScriptPool(it, nThread)
        }

        Person p = new Person()
        p.setMySchoolName("家里蹲")
        Map<String, Object> param = new HashMap<>()
        param.put("person", p)

//pool
        nThread.times { out ->
            Thread.start {
                Random random = new Random()
                long begin = System.currentTimeMillis()
                maxExec.times {
                    ScriptPool scriptPool = scriptPools[random.nextInt(ruleCount)]
                    Script script = scriptPool.borrowObject()
                    try {
                        script.setBinding(new Binding(param))
                        script.run()
                    } finally {
                        scriptPool.returnObject(script)
                    }
                }
                //println " pool 耗时 ： ${(System.currentTimeMillis() - begin)}"
                counter1.set(out, counter1.get(out) + System.currentTimeMillis() - begin)
                overFlag1.getAndIncrement()
            }
        }
        while (overFlag1.get() < 5) {
            sleep(100)
        }
        println " pool total ${counter1.sum()}"
    }


    private static test1() {
        def maxExec = 100000
        def counter1 = [0, 0, 0, 0, 0]
        def counter2 = [0, 0, 0, 0, 0]
        AtomicInteger overFlag1 = new AtomicInteger(0)
        AtomicInteger overFlag2 = new AtomicInteger(0)

        String scriptText = "person.mySchoolName + 1 ";
        // Person.class.getMethod()
        Person p = new Person()
        p.setMySchoolName("家里蹲")
        Map<String, Object> param = new HashMap<>()
        param.put("person", p)
        ScriptPool scriptPool = new ScriptPool(scriptText)

//pool
        5.times { out ->
            Thread.start {
                long begin = System.currentTimeMillis()
                maxExec.times { inner ->
                    Script script = scriptPool.borrowObject()
                    try {
                        script.setBinding(new Binding(param))
                        script.run()
                    } finally {
                        scriptPool.returnObject(script)
                    }
                }
                //println " pool 耗时 ： ${(System.currentTimeMillis() - begin)}"
                counter1.set(out, counter1.get(out) + System.currentTimeMillis() - begin)
                overFlag1.getAndIncrement()
            }
        }
//normal
        5.times { out ->
            Thread.start {
                long begin = System.currentTimeMillis()
                maxExec.times {
                    GroovyExecutor3.getInstance().invoke(scriptText, param)
                }
                //println " 普通 耗时 ： ${(System.currentTimeMillis() - begin)}"
                counter2.set(out, counter2.get(out) + System.currentTimeMillis() - begin)
                overFlag2.getAndIncrement()
            }
        }

        while (overFlag1.get() < 5 || overFlag2.get() < 5) {
            sleep(100)
        }

        println " pool total ${counter1.sum()}"
        println " normal total ${counter2.sum()}"


    }


}
