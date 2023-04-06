package com.greedy.section02.crud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

public class A_EntityManagerCRUDTests {
	
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
	public void 메뉴코드로_메뉴_조회_테스트() {
		
		//given
		int menuCode = 2;
		
		//when
		Menu foundMenu = entityManager.find(Menu.class, menuCode);
		
		//then
		assertNotNull(foundMenu);
		assertEquals(menuCode, foundMenu.getMenuCode());
		System.out.println("foundMenu = " + foundMenu);
		
	}
	
	@Test
	public void 새로운_메뉴_추가_테스트() {
		
		//given
		Menu menu = new Menu();
		menu.setMenuName("JPA 테스트용 신규 메뉴");
		menu.setMenuPrice(5000);
		menu.setCategoryCode(4);
		menu.setOrderableStatus("Y");
		
		//when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		try {
			entityManager.persist(menu);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}
		
		//then
		assertTrue(entityManager.contains(menu));
		
	}
	
	@Test
	public void 메뉴_이름_수정_테스트() {
		
		//given
		Menu menu = entityManager.find(Menu.class, 2);
		System.out.println("menu = " + menu);
		
		String menuNameToChange = "우럭스무디";
		
		//when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		try {
			menu.setMenuName(menuNameToChange);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}
		
		//then
		assertEquals(menuNameToChange, entityManager.find(Menu.class, 2).getMenuName());
		
	}
	
	@Test
	public void 메뉴_삭제하기_테스트() {
		
		//given
		Menu menuToRemove = entityManager.find(Menu.class, 1);
		
		//when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		try {
			entityManager.remove(menuToRemove);
			entityTransaction.commit();
		} catch (Exception e) {
			entityTransaction.rollback();
			e.printStackTrace();
		}
		
		//then
		Menu removedMenu = entityManager.find(Menu.class, 1);
		assertEquals(null, removedMenu);
	}
	
}
