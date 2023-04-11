package com.greedy.section02.onetomany;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OneToManyAssociationTests {
	
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
	public void 일대다_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {
		
		//given
		int categoryCode = 10;
		
		//when
		/* 일대다 연관관계의 경우 해당 테이블만 조회하고 연관 된 메뉴 테이블은 아직 조회하지 않는다. */
		CategoryAndMenu categoryAndMenu = entityManager.find(CategoryAndMenu.class, categoryCode);
		
		//then
		assertNotNull(categoryAndMenu);
		/* 출력 구문 작성 후, 사용하는 경우 연관 테이블을 조회해오는 동작이 일어난다. */
		System.out.println(categoryAndMenu);
		
	}
	
	@Test
	public void 일대다_연관관계_객체_삽입_테스트() {
		
		//given
		CategoryAndMenu categoryAndMenu = new CategoryAndMenu();
		categoryAndMenu.setCategoryCode(888);
		categoryAndMenu.setCategoryName("일대다추가카테고리");
		categoryAndMenu.setRefCategoryCode(null);
		
		List<Menu> menuList = new ArrayList<>();
		Menu menu = new Menu();
		menu.setMenuCode(777);
		menu.setMenuName("일대다아이스크림");
		menu.setMenuPrice(50000);
		menu.setOrderableStatus("Y");
		/* 부모키가 존재하지 않으면 자식 테이블에 값을 넣을 수 없다. */
		menu.setCategoryCode(categoryAndMenu.getCategoryCode());
		
		menuList.add(menu);
		
		categoryAndMenu.setMenuList(menuList);
		
		//when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(categoryAndMenu);
		entityTransaction.commit();
		
		//then
		CategoryAndMenu foundCategoryAndMenu = entityManager.find(CategoryAndMenu.class, 888);
		System.out.println(foundCategoryAndMenu);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
