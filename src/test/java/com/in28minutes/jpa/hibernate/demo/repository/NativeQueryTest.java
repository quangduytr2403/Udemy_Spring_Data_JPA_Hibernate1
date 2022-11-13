package com.in28minutes.jpa.hibernate.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.in28minutes.jpa.hibernate.demo.DemoApplication;
import com.in28minutes.jpa.hibernate.demo.entity.Course;

@SpringBootTest(classes = DemoApplication.class)
class NativeQueryTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;

	@Test
	void native_query_basic() {
		Query createQuery = em.createNativeQuery("select * from course", Course.class);
		List<?> resultList = createQuery.getResultList();
		logger.info("select c from course c where name like '100 steps' -> {}", resultList);
	}
	
	@Test
	void native_query_with_param() {
		Query createQuery = em.createNativeQuery("select * from course where id = ?", Course.class);
		createQuery.setParameter(1, 10001L);
		List<?> resultList = createQuery.getResultList();
		logger.info("select c from course c where id=? -> {}", resultList);
	}

	
	@Test
	void native_query_with_named_param() {
		Query createQuery = em.createNativeQuery("select * from course where id = :id", Course.class);
		createQuery.setParameter("id", 10001L);
		List<?> resultList = createQuery.getResultList();
		logger.info("select c from course c where id= :id' -> {}", resultList);
	}
	
	@Test
	@Transactional
	void native_query_with_update() {
		Query createQuery = em.createNativeQuery("update course set last_updated_date=CURRENT_TIMESTAMP()", Course.class);
		int noOfRow = createQuery.executeUpdate();
		logger.info("NoOfRowUpdate' -> {}", noOfRow);
	}
}
