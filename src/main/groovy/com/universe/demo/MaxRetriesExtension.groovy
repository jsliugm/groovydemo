package com.universe.demo
class MaxRetriesExtension {       //                              扩展类
    static void maxRetries(Integer self, Closure code) {        //静态方法的第一个参数对应于消息的接收者，也就是说扩展实例
        int retries = 0
        Throwable e
        while (retries<self) {
            try {
                code.call()
                break
            } catch (Throwable err) {
                e = err
                retries++
            }
        }
        if (retries==0 && e) {
            throw e
        }
    }

    static void main(String[] args) {
        int i = 0
        5.maxRetries{i++}
        println i
    }
}