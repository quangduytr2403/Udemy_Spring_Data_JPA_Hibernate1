package com.in28minutes.jpa.hibernate.demo.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;


@SpringBootTest(classes = DemoApplication.class)
class CourseSpringDataRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CourseSpringDataRepository repository;
	

	@Test
	void findById_CoursePresent() {
		Optional<Course> courseOptional = repository.findById(10001L);
		assertTrue(courseOptional.isPresent());
	}
	
	@Test
	void findById_CourseNotPresent() {
		Optional<Course> courseOptional = repository.findById(20001L);
		assertFalse(courseOptional.isPresent());
	}
	
	@Test
	void playingAroundWithSpringDataRepository() {
//		Course course = new Course("Microservices in 100 steps");
//		repository.save(course);
//		course.setName("Microservices in 100 steps - updated");
//		repository.save(course);
		logger.info("Courses -> {}", repository.findAll());
		logger.info("Count -> {}", repository.count());
	}
	
	@Test
	void sort() {
		Sort sort = Sort.by(Sort.Direction.DESC, "name").and(Sort.by(Sort.Direction.ASC, "id"));
		logger.info("Courses -> {}", repository.findAll(sort));
		logger.info("Count -> {}", repository.count());
	}
	
	@Test
	void pagination() {
		PageRequest pageRequest = PageRequest.of(1, 3);
		Page<Course> firstPage = repository.findAll(pageRequest);
		logger.info("Courses -> {}", firstPage.getContent());
	}
	
	@Test
	void findUsingName() {
		logger.info("FindByName -> {}", repository.findByName("JPA in 50 steps"));
	}
	
	@Test
	@Transactional
	void countAndDeleteUsingName() {
		logger.info("CountByName -> {}", repository.countByName("JPA in 50 steps"));
		Long deleteRecord = repository.deleteByName("JPA in 50 steps");
		logger.info("deleteRecord -> {}", deleteRecord);
	}
}
