package com.in28minutes.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Student;

@SpringBootTest(classes = DemoApplication.class)
class CriteriaQueryTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	@Test
	void all_courses() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> createQuery = criteriaBuilder.createQuery(Course.class);
		Root<Course> root = createQuery.from(Course.class);
		
		TypedQuery<Course> query = em.createQuery(createQuery.select(root));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}

	@Test
	void all_courses_having_100steps() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> createQuery = criteriaBuilder.createQuery(Course.class);
		Root<Course> root = createQuery.from(Course.class);
		Predicate like = criteriaBuilder.like(root.get("name"), "%100steps");
		createQuery.where(like);
		
		TypedQuery<Course> query = em.createQuery(createQuery.select(root));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}
	
	@Test
	void all_courses_without_students() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> createQuery = criteriaBuilder.createQuery(Course.class);
		Root<Course> root = createQuery.from(Course.class);
		Predicate studentIsEmpty = criteriaBuilder.isEmpty(root.get("students"));
		createQuery.where(studentIsEmpty);
		
		TypedQuery<Course> query = em.createQuery(createQuery.select(root));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}
	
	@Test
	void join() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> createQuery = criteriaBuilder.createQuery(Course.class);
		Root<Course> root = createQuery.from(Course.class);
		root.join("students");
		
		TypedQuery<Course> query = em.createQuery(createQuery.select(root));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}
	
	@Test
	void left_join() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Course> createQuery = criteriaBuilder.createQuery(Course.class);
		Root<Course> root = createQuery.from(Course.class);
		root.join("students", JoinType.LEFT);
		
		TypedQuery<Course> query = em.createQuery(createQuery.select(root));
		List<Course> resultList = query.getResultList();
		logger.info("Typed Query -> {}", resultList);
	}
}
