package com.cesar.test.bean;

/**
 * HibernateTest entity. @author MyEclipse Persistence Tools
 */

public class Person implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1909873700133463472L;
	// Fields

	private Integer id;
	private String name;
	private Integer age;

	// Constructors

	/** default constructor */
	public Person() {
	}

	/** full constructor */
	public Person(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "HibernateTest [id=" + id + ", name=" + name + ", age=" + age
				+ "]";
	}
	
	

}