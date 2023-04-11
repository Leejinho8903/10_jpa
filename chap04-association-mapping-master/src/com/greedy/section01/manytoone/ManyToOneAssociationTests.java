package com.greedy.section01.manytoone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManyToOneAssociationTests {
	
	/* 연관 관계란?
	 * 서로 다른 두 객체가 연관성을 가지고 관계를 맺는 것
	 * 
	 * 연관 관계의 분류
	 * 
	 * 1. 방향(Direction)에 따른 분류
	 * 참조에 의한 객체의 연관 관계는 단방향이다. 테이블의 연관 관계는 외래 키를 이용하여 양방향 연관 관계의 특징을 가진다.
	 * 객체간의 연관 관계를 양방향으로 만들고 싶을 경우 반대 쪽에서도 필드를 추가해서 참조를 보관하면 된다.
	 * 하지만 엄밀하게 이는 양방향 관계가 아니라 단방향 관계 2개로 볼 수 있다.
	 * 1-1. 단방향 연관 관계
	 * 1-2. 양방향 연관 관계
	 * 
	 * 2. 다중성(Multiplicity)에 대한 분류
	 * 연관 관계가 있는 객체 관계 혹은 테이블 관계에서 실제로 연관을 가지는(매핑되는) 객체의 수 또는 행의 수에 따라 분류된다.
	 * 2-1. 1:1(OneToOne) 연관 관계
	 * 2-2. 1:N(OneToMany) 연관 관계
	 * 2-3. N:1(ManyToOne) 연관 관계
	 * 2-4. N:N(ManyToMany) 연관 관계
	 * */
	
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
	
	/* 연관 관계를 가지는 엔티티를 조회하는 방법은 크게 2가지가 있다.
	 * 1. 객체 그래프 탐색(객체 연관관계를 사용한 조회)
	 * 2. 객체 지향 쿼리 사용(JPQL)
	 * */
	
	@Test
	public void 다대일_연관관계_객체_그래프_탐색을_이용한_조회_테스트() {
		
		//given
		int menuCode = 15;
		
		//when
		/* 다대일 연관관계의 경우 실행 된 sql문을 보면 참조 테이블을 조인해서 결과를 조회한다. */
		MenuAndCategory foundMenu = entityManager.find(MenuAndCategory.class, menuCode);
		Category menuCategory = foundMenu.getCategory();
		
		//then
		assertNotNull(menuCategory);
		System.out.println("menuCategory = " + menuCategory);
	}
	
	@Test
	public void 다대일_연관관계_객체지향쿼리_사용한_카테고리_이름_조회_테스트() {
		
		//given
		/* join 문법이 sql과는 다소 차이가 있지만 직접 쿼리를 작성할 수 있는 문법을 제공한다.
		 * 주의할 점은 FROM절에 기술할 테이블명에는 반드시 엔티티명이 작성되어야 한다. */
		String jpql = "SELECT c.categoryName FROM many_to_one_menu_and_category m JOIN m.category c WHERE m.menuCode = 15";
		
		//when
		/* 조회 시 조인 구문이 실행되며 연관 테이블을 미리 조회해온다. */
		String category = entityManager.createQuery(jpql, String.class).getSingleResult();
		
		//then
		assertNotNull(category);
		System.out.println("category = " + category);
	}
	
	@Test
	public void 다대일_연관관계_객체_삽입_테스트() {
		
		//given
		MenuAndCategory menuAndCategory = new MenuAndCategory();
		menuAndCategory.setMenuCode(99999);
		menuAndCategory.setMenuName("죽방멸치빙수");
		menuAndCategory.setMenuPrice(30000);
		
		Category category = new Category();
		category.setCategoryCode(33333);
		category.setCategoryName("신규카테고리");
		category.setRefCategoryCode(null);
		
		menuAndCategory.setCategory(category);
		menuAndCategory.setOrderableStatus("Y");
		
		//when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		/* commit을 할 경우 flush 하며 컨텍스트 내의 영속성 객체를 insert 하는 쿼리를 동작시키는데 
		 * 부모 테이블(TBL_CATEGORY)에 값이 먼저 들어있어야 자식 테이블(TBL_MENU)에 데이터를 넣을 수 있다.
		 * @ManyToOne 어노테이션에 영속성 전이 설정을 해주어야 한다.
		 * 영속성 전이? 특정 엔티티를 영속화할 때 연관된 엔티티도 함께 영속화 한다는 의미이다.
		 * cascade=CascadeType.PERSIST를 설정하면 Menu를 저장하기 전에 Category부터 저장하게 된다. 
		 * */
		entityManager.persist(menuAndCategory);
		entityTransaction.commit();
		
		//then
		MenuAndCategory foundMenuAndCategory = entityManager.find(MenuAndCategory.class, 99999);
		assertEquals(99999, foundMenuAndCategory.getMenuCode());
		assertEquals(33333, foundMenuAndCategory.getCategory().getCategoryCode());

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}







