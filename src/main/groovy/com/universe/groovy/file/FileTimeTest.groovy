package com.universe.groovy.file

import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime

class FileTimeTest {


    public static void main(String[] args) {
        File file = new File("/home/universe/IdeaProjects/groovydemo/helloworld.txt")
        println file.exists()

        BasicFileAttributes attributes =  Files.readAttributes(file.toPath(), BasicFileAttributes)


        println new Date(attributes.lastAccessTime().toMillis())
        println new Date(attributes.creationTime().toMillis())
        println new Date(attributes.lastModifiedTime().toMillis())


        FileTime obj =  Files.getAttribute(file.toPath(),'unix:ctime')

        //
        println  "ctime ${new Date(obj.toMillis())}"
    }

}
