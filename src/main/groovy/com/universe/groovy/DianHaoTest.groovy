package com.universe.groovy

class DianHaoTest {
     static void main(String[] args) {
        def mc = new ExpandoMetaClass(Integer.class,true)
         mc.getYuan <<
                 {
                     ->
                     delegate as float
                 }
         mc.getJiao <<
                 {
                     ->
                     def j = delegate as float
                     j/10
                 }
         mc.getFen <<
                 {
                     ->
                     def f = delegate as float
                     f/100
                 }
         mc.initialize()
         println 12.yuan + 30.jiao + 50.fen
    }
}