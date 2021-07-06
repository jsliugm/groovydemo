package com.universe.groovy

import com.universe.java.Student

class MopTest{
    static void main(String[] args) {
        Student student = new Student()
        16.times {
            //println 'student-'+it
            //student.name = 'abc'
            student.setName('abc')
        }
        Teacher teacher = new Teacher()
        16.times {
            //println 'teacher'+it
            teacher.name = 'zs'
        }
    }
}

