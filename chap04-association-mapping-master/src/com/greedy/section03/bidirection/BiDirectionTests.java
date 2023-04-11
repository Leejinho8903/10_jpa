package com.greedy.section03.bidirection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BiDirectionTests {

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
	
	/* 양방향 연관관계 매핑
	 * 데이터베이스의 테이블은 외래키 하나로 양방향 조회가 가능하지만 객체는 서로 다른 두 단방향 참조를 합쳐서 양방향이라고 한다.
	 * 따라서 두 개의 연관 관계 중 연관 관계의 주인을 정하고, 주인이 아닌 연관 관계를 하나 더 추가하는 방식으로 작성하게 된다.
	 * 반대 방향으로도 access하여 객체 그래프 탐색을 할 일이 많은 경우 양방향 연관관계 매핑을 사용한다. (항상 사용하는 것이 아님)
	 * */
	
	/* 연관 관계의 주인을 정하는 기준
	 * 양방향 연관 관계시 연관관계의 주인(Owner)라는 이름으로 인해 오해가 있을 수 있다.
	 * 비즈니스 로직 상 더 중요하다고 연관 관계의 주인으로 선택하면 안되며,
	 * 비즈니스 중요도를 배제하고 단순히 외래키 관리자의 의미를 부여해야 한다.
	 * 연관 관계의 주인은 외래키를 가지고 있는 엔티티이다.
	 * */
	
	@Test
	public void 양방향_연관관계_매핑_조회_테스트() {
		
		//given
		int menuCode = 10;
		int categoryCode = 10;
		
		//when
		/* 진짜 연관 관계는 처음 조회 시부터 조인한 결과를 인출해온다. */
		Menu foundMenu = entityManager.find(Menu.class, menuCode);
		/* 가짜 연관 관계는 해당 엔티티를 조회하고 필요 시 연관 관계 엔티티를 조회하는 쿼리를 다시 실행하게 된다. */
		Category foundCategory = entityManager.find(Category.class, categoryCode);
		
		//then
		assertEquals(menuCode, foundMenu.getMenuCode());
		assertEquals(categoryCode, foundCategory.getCategoryCode());
		
		/* 주의사항!
		 * toString() 오버라이딩 시 양방향 연관 관계는 재귀호출이 일어나기 때문에 stackOverFlowError가 발생하게 된다.
		 * 따라서 재귀가 일어나지 않게 하기 위해서는 엔티티의 주인이 아닌 쪽의 toString을 연관 객체 부분이 출력 되지 않도록 해야 한다.
		 * 특히 자동 완성 및 롬복 라이브러리를 이용하는 경우 해당 문제 발생 가능성이 매우 높아진다.
		 * */
		System.out.println(foundMenu);
		System.out.println(foundCategory);
		
		/* category에 포함 된 메뉴 목록 출력 구문을 작성하고 나면 실제 사용에 필요해지기 때문에 가짜 연관 관계에 해당하는 엔티티도
		 * 다시 조회하는 쿼리가 한 번 더 동작한다. */
		foundCategory.getMenuList().forEach(System.out::println);
	}
	
	@Test
	public void 양방향_연관관계_주인_객체를_이용한_삽입_테스트() {
		
		//given
		Menu menu = new Menu();
		menu.setMenuCode(125);
		menu.setMenuName("연관관계주인메뉴");
		menu.setMenuPrice(10000);
		menu.setOrderableStatus("Y");
		
		/* 양방향 연관관계를 설정하고 흔히 하는 실수는 연관관계의 주인에는 값을 입력하고, 주인이 아닌 곳에는 값을 입력하지 않는 경우
		 * 외래키 컬럼이 not null 제약조건이 설정되어 있는 경우이다. null 값이 외래키 컬럼에 삽입 되지 않으므로 에러가 발생한다.
		 * 따라서 카테고리 정보를 추가한다. */
		menu.setCategory(entityManager.find(Category.class, 4));
		
		//when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(menu);
		entityTransaction.commit();
		
		//then
		Menu foundMenu = entityManager.find(Menu.class, menu.getMenuCode());
		assertEquals(menu.getMenuCode(), foundMenu.getMenuCode());
		System.out.println(foundMenu);
	}
	
	@Test
	public void 양방향_연관관계_주인이_아닌_객체를_이용한_삽입_테스트() {
		
		//given
		Category category = new Category();
		category.setCategoryCode(1004);
		category.setCategoryName("양방향카테고리");
		category.setRefCategoryCode(null);
		
		//when
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(category);
		entityTransaction.commit();
		
		//then
		Category foundCategory = entityManager.find(Category.class, category.getCategoryCode());
		assertEquals(category.getCategoryCode(), foundCategory.getCategoryCode());
		System.out.println(foundCategory);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
