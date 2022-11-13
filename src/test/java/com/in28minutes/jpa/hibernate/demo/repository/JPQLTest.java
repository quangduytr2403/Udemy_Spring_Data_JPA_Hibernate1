package com.in28minutes.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Student;

@SpringBootTest(classes = DemoApplication.class)
class JPQLTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	@Test
	void jpql_basic() {
		Query createQuery = em.createQuery("select c from Course c");
		List<?> resultList = createQuery.getResultList();
		logger.info("select c from course c -> {}", resultList);
	}

	@Test
	void jpql_typed() {
		TypedQuery<Course> createQuery = em.createQuery("select c from Course c", Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("select c from course c -> {}", resultList);
	}
	
	@Test
	void jpql_where() {
		TypedQuery<Course> createQuery = em.createQuery("select c from Course c where name like '%100 steps'", Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("select c from course c where name like '100 steps' -> {}", resultList);
	}
	
	@Test
	void jpql_courses_without_students() {
		TypedQuery<Course> createQuery = em.createQuery("select c from Course c where c.students is empty", Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("Result -> {}", resultList);
	}
	
	@Test
	void jpql_courses_with_at_least_2_students() {
		TypedQuery<Course> createQuery = em.createQuery("select c from Course c where size(c.students) >= 2", Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("Result -> {}", resultList);
	}
	
	@Test
	void jpql_courses_orderby_students() {
		TypedQuery<Course> createQuery = em.createQuery("select c from Course c order by size(c.students) desc", Course.class);
		List<Course> resultList = createQuery.getResultList();
		logger.info("Result -> {}", resultList);
	}
	
	@Test
	void jpql_students_with_passports_in_a_certain_pattern() {
		TypedQuery<Student> createQuery = em.createQuery("select s from Student s where s.passport.number like '%1234%'", Student.class);
		List<Student> resultList = createQuery.getResultList();
		logger.info("Result -> {}", resultList);
	}
	
	//JOIN => Select c, s from Course c JOIN c.students s
	//LEFT JOIN => Select c, s from Course c LEFT JOIN c.students s
	//CROSS JOIN => Select c,s from Course c, Student s
	@Test
	void join() {
		Query query = em.createQuery("Select c, s from Course c JOIN c.students s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Result Size -> {}", resultList.size());
		for(Object[] result : resultList) {
			logger.info("Course -> {} Student -> {}", result[0], result[1]);
		}
	}
	
	@Test
	void left_join() {
		Query query = em.createQuery("Select c, s from Course c LEFT JOIN c.students s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Result Size -> {}", resultList.size());
		for(Object[] result : resultList) {
			logger.info("Course -> {} Student -> {}", result[0], result[1]);
		}
	}
	
	@Test
	void cross_join() {
		Query query = em.createQuery("Select c,s from Course c, Student s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Result Size -> {}", resultList.size());
		for(Object[] result : resultList) {
			logger.info("Course -> {} Student -> {}", result[0], result[1]);
		}
	}
}
