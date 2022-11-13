package com.in28minutes.jpa.hibernate.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Review;

@SpringBootTest(classes = DemoApplication.class)
class PerformanceTuningTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseRepository repository;
	
	@Autowired
	EntityManager em;

	@Test
	@Transactional
	void creatingNPusOneProblem() {
		List<Course> courses = 
				em.createNamedQuery("query_get_all_courses", Course.class).getResultList();
		for(Course course : courses) {
			logger.info("Course -> {} Students -> {}", course, course.getStudents());
		}
	}
	
	@Test
	@Transactional
	void solving_EntityGraph() {
		EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
		Subgraph<Object> subgraph = entityGraph.addSubgraph("students");
		
		List<Course> courses = 
				em.createNamedQuery("query_get_all_courses", Course.class)
				.setHint("javax.persistence.loadgraph", entityGraph)
				.getResultList();
		for(Course course : courses) {
			logger.info("Course -> {} Students -> {}", course, course.getStudents());
		}
	}
	
	@Test
	@Transactional
	void solving_join_fetch() {
		List<Course> courses = 
				em.createNamedQuery("query_get_all_courses_join_fetch", Course.class).getResultList();
		for(Course course : courses) {
			logger.info("Course -> {} Students -> {}", course, course.getStudents());
		}
	}
}
