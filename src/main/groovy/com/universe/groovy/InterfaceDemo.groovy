package com.universe.groovy

def t1 = new Thread({
    println 'hello world'
} as Runnable);
t1.start()