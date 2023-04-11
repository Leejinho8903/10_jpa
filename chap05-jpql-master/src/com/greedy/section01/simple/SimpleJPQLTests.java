package com.greedy.section01.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleJPQLTests {
	
	/* JPQL(Java Persistence Query Language) 
	 * : 엔티티 객체를 중심으로 개발할 수 있는 객체 지향 쿼리
	 *   SQL보다 간결하며 DBMS에 상관 없이 개발이 가능하다.
	 *   (방언을 통해 해결되며 해당 DBMS에 맞는 SQL을 실행한다. 특정 DBMS에 의존하지 않는다.)
	 *   JPQL은 find() 메소드를 통한 조회와 다르게 항상 데이터베이스에 SQL을 실행해서 결과를 조회한다.
	 *   (영속성 컨텍스트에 이미 존재하면 기존 엔티티를 반환하고 조회한 것은 버린다.)
	 *   JPQL은 엔티티 객체를 대상으로 쿼리를 질의하고 SQL은 데이터베이스의 테이블을 대상으로 질의한다.
	 *   즉, JQPL은 결국 SQL로 변환 된다.
	 *   */
	
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
	
	/* JPQL의 기본 문법
	 * 1. select
	 * select
	 * from
	 * [where]
	 * [group by]
	 * [having]
	 * [order by]
	 * 
	 * 2. insert : EntityManager가 제공하는 persist() 메소드를 사용하면 된다.
	 * 
	 * 3. update
	 * update
	 * [where]
	 * 
	 * 4. delete
	 * delete
	 * [where]
	 * 
	 * 특징
	 * 엔티티와 속성은 대소문자를 구분한다.
	 * select, from과 같은 기본 키워드는 대소문자를 구분하지 않는다.
	 * 별칭을 필수로 사용해야 하며 별칭 없이 작성하면 에러가 발생한다.
	 * */
	
	/* JPQL 사용 방법
	 * 1. 작성한 JPQL(문자열)을 em.createQuery 메소드를 통해 쿼리 객체로 만든다. 쿼리 객체는 TypedQuery, Query 두 가지가 있다.
	 * - TypedQuery : 반환할 타입을 명확하게 지정하는 방식일 때 사용(쿼리 객체의 메소드 실행 결과로 지정한 타입이 반환 된다.)
	 * - Query : 반환할 타입을 명확하게 지정할 수 없을 때 사용(쿼리 객체 매소드의 실행 결과로 Object or Object[]이 반환 된다.)
	 * 2. 쿼리 객체에서 제공하는 메소드 getSingleResult() 또는 getResultList()를 호출해서 쿼리를 실행하고 데이터베이스를 조회한다.
	 * - getSingleResult() : 결과가 정확히 한 행일경우 사용 (없거나 많으면 예외 발생)
	 * - getResultList() : 결과가 2행 이상일 경우 사용하며 컬렉션을 반환한다. (결과가 없으면 빈 컬렉션 반환)
	 * */
	@Test
	public void TypedQuery를_이용한_단일메뉴_조회_테스트() {
		
		//when
		String jpql = "SELECT m.menuName FROM section01_menu as m WHERE m.menuCode = 7";
		TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
		
		String resultMenuName = query.getSingleResult();
		
		//then
		assertEquals("민트미역국", resultMenuName);
		
	}
	
	@Test
	public void Query를_이용한_단일메뉴_조회_테스트() {
		
		//when
		String jpql = "SELECT m.menuName FROM section01_menu as m WHERE m.menuCode = 7";
		Query query = entityManager.createQuery(jpql);		//결과 값의 타입을 명시하지 않음
		
		Object resultMenuName = query.getSingleResult();	//결과 값은 Object로 반환 된다
		
		//then
		assertTrue(resultMenuName instanceof String);
		assertEquals("민트미역국", resultMenuName);
		
	}
	
	@Test
	public void JPQL을_이용한_단일행_조회_테스트() {
		
		//when
		String jpql = "SELECT m FROM section01_menu as m WHERE m.menuCode = 7";
		TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);	//반환 타입을 row와 매핑할 엔티티 타입으로 설정
		
		Menu foundMenu = query.getSingleResult();
		
		//then
		assertEquals(7, foundMenu.getMenuCode());
		System.out.println(foundMenu);
		
	}
	
	@Test
	public void TypedQuery를_이용한_여러행_조회_테스트() {
		
		//when
		String jpql = "SELECT m FROM section01_menu as m";
		TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);	//반환 타입을 row와 매핑할 엔티티 타입으로 설정
		
		List<Menu> foundMenuList = query.getResultList();
		
		//then
		assertNotNull(foundMenuList);
		foundMenuList.forEach(System.out::println);
		
	}
	
	@Test
	public void Query를_이용한_여러행_조회_테스트() {
		
		//when
		String jpql = "SELECT m FROM section01_menu as m";
		Query query = entityManager.createQuery(jpql);	
		
		List<Menu> foundMenuList = query.getResultList();
		
		//then
		assertNotNull(foundMenuList);
		foundMenuList.forEach(System.out::println);
		
	}
	
	/* 연산자는 SQL과 다르지 않으므로 몇가지 종류의 연산자만 테스트 해본다. */
	
	@Test
	public void distinct를_활용한_중복제거_여러_행_조회_테스트() {
		
		//when
		String jpql = "SELECT DISTINCT m.categoryCode FROM section01_menu m";
		TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
		List<Integer> categoryCodeList = query.getResultList();
		
		//then
		assertNotNull(categoryCodeList);
		categoryCodeList.forEach(System.out::println);
		
	}
	
	@Test
	public void in_연산자를_활용한_조회_테스트() {
		/* categoryCode가 6, 10 인 메뉴 목록 조회 출력 */
		
        //when
        String jpql = "SELECT m FROM section01_menu m WHERE m.categoryCode IN (6, 10)";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
	}
	
	@Test
	public void like_연산자를_활용한_조회_테스트() {
		/* 마늘이 들어가는 메뉴명을 가진 메뉴 목록 조회 출력 */
		
        //when
        String jpql = "SELECT m FROM section01_menu m WHERE m.menuName LIKE '%마늘%'";
        List<Menu> menuList = entityManager.createQuery(jpql, Menu.class).getResultList();

        //then
        assertNotNull(menuList);
        menuList.forEach(System.out::println);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
