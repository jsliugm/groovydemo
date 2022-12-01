package com.universe.groovy.UsingClosures

def tellFortunes(closure) {
    Date date = new Date("09/20/2012")
//closure date，"Your day is filled with ceremony"
//closure date，"They're features，not bugs"
// 可以通过科里化避免重复发送date
    def postFortune = closure.curry(date)
    postFortune "Your day is filled with ceremony"
    postFortune "They're features，not bugs"

}

tellFortunes() {
    date, fortune ->
        println "Fortune for ${date} is '${fortune}'"
}