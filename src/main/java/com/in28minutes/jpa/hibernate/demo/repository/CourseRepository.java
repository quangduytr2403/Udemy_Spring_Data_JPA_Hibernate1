package com.in28minutes.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.in28minutes.jpa.hibernate.demo.entity.Course;
import com.in28minutes.jpa.hibernate.demo.entity.Review;
import com.in28minutes.jpa.hibernate.demo.entity.ReviewRating;

@Repository
@Transactional
public class CourseRepository {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	public Course findById(Long id) {
		return em.find(Course.class, id);
	}
	
	public Course save(Course course) {
		if(course.getId() == null) {
			em.persist(course);
		} else {
			em.merge(course);
		}
		
		return course;
	}
	
	public void deleteById(Long id) {
		Course course = findById(id);
		em.remove(course);
	}
	
	public void playWithEntityManager() {
		Course course1 = new Course("Web services in 100 steps");
		em.persist(course1);
		
		Course course2 = findById(10001L);
		course2.setName("JPA in 50 steps - update");
	}

	public void addHardcodeReviewsForCourse() {
		Course course = findById(10003L);
		logger.info("course.getReviews() -> {}", course.getReviews());
		
		Review review1 = new Review(ReviewRating.FIVE, "Great Hand-on stuff");
		Review review2 = new Review(ReviewRating.FIVE, "Hatsoff");
		
		//Setting relationship
		course.addReview(review1);
		review1.setCourse(course);
		course.addReview(review2);
		review2.setCourse(course);
		
		//Save to database
		em.persist(review1);
		em.persist(review2);
	}
	
	public void addReviewsForCourse(Long courseId, List<Review> reviews) {
		Course course = findById(courseId);
		logger.info("course.getReviews() -> {}", course.getReviews());
		
		for(Review review : reviews) {
			//Setting relationship
			course.addReview(review);
			review.setCourse(course);
	
			//Save to database
			em.persist(review);
		}
	}
}
