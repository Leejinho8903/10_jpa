package com.greedy.section01.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Entity {

	private static EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	@BeforeAll
	public static void initFactory() {
		entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
	}
	
	@BeforeEach
	public void initManager() {
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	@AfterAll
	public static void closeFactory() {
		entityManagerFactory.close();
	}
	
	@AfterEach
	public void closeManager() {
		entityManager.close();
	}
	
	@Test
	public void 카테고리코드로_카테고리이름_조회_테스트() {
		
		Category category = entityManager.find(Category.class, 4);
		
		assertEquals(4, category.getCategoryCode());
		System.out.println(category.getCategoryCode() + "번 카테고리 : " + category.getCategoryName());
	}
	
	@Test
	public void 카테고리_추가_테스트() {
		
		Category category = new Category();
		category.setCategoryName("북양");
		category.setRefCategoryCode(1);
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		try {
			entityManager.persist(category);
			entityTransaction.commit();
		} catch(Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		
		assertTrue(entityManager.contains(category));
	}
	
	@Test
	public void 카테고리이름_수정_테스트() {
		
		Category category = entityManager.find(Category.class, 21);
		
		category.setCategoryName("남양");
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		try {
			entityManager.persist(category);
			entityTransaction.commit();
		} catch(Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		
		assertEquals("남양", entityManager.find(Category.class, 21).getCategoryName());
	}
	
	@Test
	public void 카테고리_삭제_테스트() {
		
		Category category = entityManager.find(Category.class, 21);
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		try {
			entityManager.remove(category);
			entityTransaction.commit();
		} catch(Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}
		
		assertEquals(null, entityManager.find(Category.class, 21));
	}
	
	
	
}