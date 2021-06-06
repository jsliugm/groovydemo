package com.universe.groovy

class XingHaoTest {
    static void main(String[] args) {
        List list = [*(0..10)]
        println '[==============list=================]'
        println list
        //clone
        println '[===========list2 created by *==================]'
        def list2 = [*list]
        println list2
        println list==list2
        println list.is(list2)
        def list3 = list.clone()
        println '[===========list3 created by clone()==================]'
        println list3
        println list3==list
        println list3.is(list)
        println '[==============map==============]'
        Map map = ['tom':12,'andy':11]
        println map
        def map2 = [*:map,'tony':123]
        println map2
    }
}