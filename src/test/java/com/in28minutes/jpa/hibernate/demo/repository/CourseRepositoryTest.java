package com.in28minutes.jpa.hibernate.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;
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
class CourseRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseRepository repository;
	
	@Autowired
	EntityManager em;

	@Test
	void findById_basic() {
		Course course = repository.findById(10002L);
		assertEquals("Spring in 50 steps", course.getName());
	}
	
	@Test
	void findById_firstLevelCacheDemo() {
		Course course = repository.findById(10002L);
		logger.info("First Course Retrive");
		Course course1 = repository.findById(10002L);
		logger.info("First Course Retrive again");
		assertEquals("Spring in 50 steps", course.getName());
		assertEquals("Spring in 50 steps", course1.getName());
	}

	@Test
	@DirtiesContext
	void deleteById_basic() {
		repository.deleteById(10002L);
		assertNull(repository.findById(10002L));
	}
	
	@Test
	@DirtiesContext
	void save_basic() {
		//get a course
		Course course = repository.findById(10001L);
		assertEquals("JPA in 50 steps", course.getName());
		
		//update and save
		course.setName("JPA in 50 steps - updated");
		repository.save(course);
		
		//check value
		Course course1 = repository.findById(10001L);
		assertEquals("JPA in 50 steps - updated", course1.getName());
	}
	
	@Test
	@DirtiesContext
	void playWithEntityManager() {
		repository.playWithEntityManager();
	}
	
	@Test
	@Transactional
	void retrieveReviewsForCourse() {
		Course course = repository.findById(10001L);
		logger.info("course.getReviews() -> {}", course.getReviews());
	}
	
	@Test
	@Transactional
	void retrieveCourseForReview() {
		Review review = em.find(Review.class, 50001L);
		logger.info("{}", review.getCourse());
	}
}
