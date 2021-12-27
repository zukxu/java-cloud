package com.zukxu.java8.test.newClass;

import java.io.Serializable;

/**
 * @author zukxu
 * CreateTime: 2021/7/7 0007 21:59
 */
public class Student implements Cloneable, Serializable {
	private String no;
	private String name;
	private String sex;

	public Student() {
	}

	public Student(String no, String name, String sex) {
		this.no = no;
		this.name = name;
		this.sex = sex;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Student{" + "no='" + no + '\'' + ", name='" + name + '\'' + ", sex='" + sex + '\'' + '}';
	}
	@Override
	public Student clone() {
		Student student = null;
		try {
			student = (Student) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return student;
	}
}
