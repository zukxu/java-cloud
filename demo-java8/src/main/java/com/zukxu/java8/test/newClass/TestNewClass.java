package com.zukxu.java8.test.newClass;

import com.zukxu.java8.model.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author zukxu
 * CreateTime: 2021/7/7 0007 22:02
 */
public class TestNewClass {
    public static void main(String[] args) {
        // newKeyword();
        // classInstance();
        // reflectInstance();
        // cloneInstance();
        serializeInstance();
    }

    /**
     * new 关键字创建实例
     */
    public static void newKeyword() {
        Student s1 = new Student();
        Student s2 = new Student("1", "掌上", "男");

        System.out.println(s1);
        System.out.println(s2);
    }

    /**
     * Class的方法创建实例
     */
    public static void classInstance() {
        try {
            Student s1 = (Student) Class.forName("com.zukxu.test.newClass.Student").newInstance();
            Student s2 = Student.class.newInstance();
            System.out.println(s1);
            System.out.println(s2);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射的方法创建实例
     */
    public static void reflectInstance() {
        try {
            Constructor<Student> constructor = Student.class.getConstructor();
            Student s1 = constructor.newInstance();
            System.out.println(s1);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void cloneInstance() {
        Student stu1 = new Student("1", "clone", "男");

        Student clone = stu1.clone();
        System.out.println(clone);
    }

    public static void serializeInstance() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("S"));
            Student stu3 = (Student) in.readObject();
            System.out.println(stu3);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

