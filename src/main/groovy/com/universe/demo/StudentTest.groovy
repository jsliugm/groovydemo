package com.universe.demo

class StudentTest {
    static void main(String[] args) {
        com.universe.demo.model.Student student = new com.universe.demo.model.Student("zs")

        while (true) {
            16.times {
                def b = System.currentTimeMillis()
                println "$it $student.name " + (System.currentTimeMillis() - b)
            }

            2.times {
                byte[] b = new byte[16 * 1024 * 1024]
                Thread.sleep(300)
            }
        }
    }
}
