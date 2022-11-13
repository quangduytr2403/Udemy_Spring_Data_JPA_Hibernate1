package com.in28minutes.jpa.hibernate.demo.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Passport {
	@Id
	@GeneratedValue
	private Long id;
	
	private String number;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy = "passport")
	private Student student;
	
	public Passport() {
	}

	public Passport (String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String name) {
		this.number = name;
	}

	public Long getId() {
		return id;
	}
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Passport [number=" + number + "]";
	}
}
