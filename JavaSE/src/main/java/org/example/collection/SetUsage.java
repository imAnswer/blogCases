package org.example.collection;

import java.util.TreeSet;

public class SetUsage {

    public void treeTest(){
        Student s1 = new Student(1);
        TreeSet<Student> s2 = new TreeSet<>();
        s2.add(s1);
    }
    

    public static void main(String[] args) {
        SetUsage setUsage = new SetUsage();
        setUsage.treeTest();
    }

    //必须实现Comparable接口才能存入TreeSet中
    class Student implements Comparable<Student> {

        int age;

        @Override
        public int compareTo(Student o) {
            return this.age - o.age;
        }

        public Student(int age) {this.age = age;}
    }

}
