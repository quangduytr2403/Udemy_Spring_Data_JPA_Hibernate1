package com.in28minutes.jpa.hibernate.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.in28minutes.jpa.hibernate.demo.entity.Course;

public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
	List<Course> findByName(String name);
	List<Course> findByNameAndId(String name, Long Id);
	List<Course> findByNameOrderByIdDesc(String name);
	Long countByName(String name);
	Long deleteByName(String name);
	
	@Query("Select c from Course c where name like '%100 Steps'")
	List<Course> courseWith100StepsInName();
	
	@Query(value="Select * from Course where name like '%100 Steps'", nativeQuery = true)
	List<Course> courseWith100StepsInNameNative();
	
	@Query(name = "query_get_100_steps")
	List<Course> courseWith100StepsInNameNamed();
	
}
