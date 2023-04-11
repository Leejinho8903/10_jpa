package com.greedy.section04.paging;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PagingTests {
	
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
	
	/* 반복적이고 지저분한 페이징 처리용 SQL은 심지어 DBMS에 따라 각각 문법이 다른 문제점을 안고 있다. 
	 * JPA는 이러한 페이징을 API를 통해 추상화해서 간단하게 처리할 수 있도록 제공해준다. 
	 * */
	
	@Test
	public void 페이징_API를_이용한_조회_테스트() {
		
		//given
		int offset = 10;		//조회를 건너 뛸 행 수
		int limit = 5;		//조회할 행 수
		
		//when
		String jpql = "SELECT m FROM section04_menu m ORDER BY m.menuCode DESC";
		
		/* 쿼리 실행 결과를 보면 offset과 limit을 활용하는 문법으로 실행되어 있는데 이는 오라클 12버전 이후 추가된 문법이다.
		 * 오라클 11이하 버전에서는 rownum을 이용한 인라인뷰 방식으로 쿼리가 동작한다. */
		List<Menu> menuList = entityManager.createQuery(jpql, Menu.class)
				.setFirstResult(offset)		//조회를 시작할 위치(0부터 시작)
				.setMaxResults(limit)		//조회할 데이터의 수
				.getResultList();
		
		//then
		assertNotNull(menuList);
		menuList.forEach(System.out::println);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
