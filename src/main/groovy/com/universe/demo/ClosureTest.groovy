package com.universe.demo


def c1 = {
    println delegate
    println this
    println thisObject
    println owner
}
c1()
//println c2
println "test c2"
Person person = new Person()
def c2 = c1.clone()
c2.owner = person
c2.delegate = person
c2.thisObject = person
c2()
//println c3
println "test c3 rehydrate"
def c3 = c1.rehydrate(person,person,person)
c3()