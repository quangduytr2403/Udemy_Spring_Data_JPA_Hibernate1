package com.in28minutes.jpa.hibernate.demo.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Passport;
import com.in28minutes.jpa.hibernate.demo.entity.Student;

@Repository
@Transactional
public class StudentRepository {
	
	@Autowired
	EntityManager em;
	
	public Student findById(Long id) {
		return em.find(Student.class, id);
	}
	
	public Student save(Student student) {
		if(student.getId() == null) {
			em.persist(student);
		} else {
			em.merge(student);
		}
		
		return student;
	}
	
	public void deleteById(Long id) {
		Student student = findById(id);
		em.remove(student);
	}
	
	public void saveStudentWithPassport() {
		Passport passport = new Passport("Z123456");
		em.persist(passport);
		Student student = new Student("Mike");
		student.setPassport(passport);
		em.persist(student);
	}
	
	public void insertHardcodeStudentAndCourse() {
		Student student = new Student("Jack");
		Course course = new Course("Microservices in 100 steps");
		
		student.addCourse(course);
		course.addStudent(student);
		
		em.persist(course);
		em.persist(student);
	}
	
	public void insertStudentAndCourse(Student student, Course course) {
		student.addCourse(course);
		course.addStudent(student);
		
		em.persist(course);
		em.persist(student);
	}
}
