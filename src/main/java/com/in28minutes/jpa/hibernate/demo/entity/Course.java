package com.in28minutes.jpa.hibernate.demo.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;


@Entity
@NamedQueries(value= {
		@NamedQuery(name="query_get_100_steps", query = "Select c from Course c where name like '%100 Steps'"),
		@NamedQuery(name="query_get_all_courses", query = "Select c from Course c"),
		@NamedQuery(name="query_get_all_courses_join_fetch", query = "Select c from Course c JOIN FETCH c.students")
})
@Cacheable
@SQLDelete(sql="update course set is_deleted=true where id=?")
@Where(clause="is_deleted = false")
public class Course {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "course")
	private List<Review> reviews = new ArrayList<>(); 
	
	@ManyToMany(mappedBy = "courses")
	private List<Student> students = new ArrayList<>(); 
	
	@UpdateTimestamp
	private Timestamp lastUpdatedDate;
	
	@CreationTimestamp
	private Timestamp createdDate;
	
	private boolean isDeleted;
	
	@PreRemove
	private void preRemove() {
		this.isDeleted = true;
	}
	
	public Course() {
	}

	public Course (String name) {
		this.name = name;
	}
	
	public Course(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}

	public void addReview(Review review) {
		this.reviews.add(review);
	}
	
	public void removeReview(Review review) {
		this.reviews.remove(review);
	}
	
	public List<Student> getStudents() {
		return students;
	}

	public void addStudent(Student student) {
		this.students.add(student);
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Course [name=" + name + "]";
	}
}
