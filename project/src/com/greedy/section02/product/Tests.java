package com.greedy.section02.product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tests {

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
	public void 테스트() {
		
		Product product = new Product();
		product.setProductName("새우깡");
		product.setProductCategory(Category.식품);
		product.setProductPrice("3000");
		product.setReleaseDate(new java.sql.Date(System.currentTimeMillis()));
		
		Product product1 = new Product();
		product1.setProductName("나이ㅋ");
		product1.setProductCategory(Category.의류);
		product1.setProductPrice("30000");
		product1.setReleaseDate(new java.sql.Date(System.currentTimeMillis()));
		
		EntityTransaction tr = entityManager.getTransaction();
		tr.begin();
		entityManager.persist(product);
		entityManager.persist(product1);
		tr.commit();

		
	}
}