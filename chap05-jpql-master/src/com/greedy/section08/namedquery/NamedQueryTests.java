package com.greedy.section08.namedquery;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NamedQueryTests {
	
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
	
	/* 동적쿼리 : 현재 우리가 사용하는 방식처럼 EntityManager가 제공하는 메소드를 이용하여 JPQL을 문자열로 런타임 시점에 동적으로 쿼리를 만드는 방식
	 *          (동적으로 만들어질 쿼리를 위한 조건식이나 반복문은 자바 코드를 이용할 수 있다.)
	 * 정적쿼리 : 미리 쿼리를 정의하고 변경하지 않고 사용하는 쿼리를 말하며 미리 정의한 코드는 이름을 부여해서 사용하게 된다.
	 *          이를 NamedQuery라고 한다. 어노테이션 방식과 xml 방식 두 가지가 있는데 쿼리가 복잡할수록 xml 방식이 선호 된다.
	 * */
	
	@Test
	public void 동적쿼리를_이용한_조회_테스트() {
		
		//given
		String searchName = "한우";
		int searchCategoryCode = 4;
		
		//when
		StringBuilder jpql = new StringBuilder("SELECT m FROM section08_menu m ");
		if(searchName != null && !searchName.isEmpty() && searchCategoryCode > 0) {
			jpql.append("WHERE ");
			jpql.append("m.menuName LIKE '%' || :menuName || '%' ");
			jpql.append("AND ");
			jpql.append("m.categoryCode = :categoryCode ");			
		} else {
			
			if(searchName != null && !searchName.isEmpty()) {
				jpql.append("WHERE ");
				jpql.append("m.menuName LIKE '%' || :menuName || '%' ");
			} else if(searchCategoryCode > 0) {
				jpql.append("WHERE ");
				jpql.append("m.categoryCode = :categoryCode ");			
			}
		}
		
		TypedQuery<Menu> query = entityManager.createQuery(jpql.toString(), Menu.class);
		
		if(searchName != null && !searchName.isEmpty() && searchCategoryCode > 0) {
			query.setParameter("menuName", searchName);
			query.setParameter("categoryCode", searchCategoryCode);
		} else {
			if(searchName != null && !searchName.isEmpty()) {
				query.setParameter("menuName", searchName);
			} else if(searchCategoryCode > 0) {
				query.setParameter("categoryCode", searchCategoryCode);
			}
		}
		
		List<Menu> menuList = query.getResultList();
		
		//then
		assertNotNull(menuList);
		menuList.forEach(System.out::println);
		
	}
	
	/* 동적 SQL을 작성하기에 JPQL은 많이 어렵다. 컴파일 에러가 발생하는 것이 아니기 때문에 쿼리를 매번 실행해서 확인해야 하는 불편함이 있다.
	 * Criteria나 queryDSL을 활용하면 보다 편리하게 작성할 수 있으며, 쿼리 작성 시 컴파일 에러로 잘못 된 부분을 확인할 수 있어 작성하기도 편하다.
	 * 마이바티스를 혼용하거나 마이바티스의 구문 빌더 API를 활용해도 좋다.
	 * */
	
	@Test
	public void 어노테이션_기반_네임드쿼리를_이용한_조회_테스트() {
		
		//when
		List<Menu> menuList = entityManager.createNamedQuery("section08_menu.selectMenuList", Menu.class).getResultList();
		
		//then
		assertNotNull(menuList);
		menuList.forEach(System.out::println);
	}
	
	/* 조금 더 복잡한 형태의 쿼리를 작성해야 하는 경우에는 xml 방식을 더 선호한다. (문자열로 쿼리를 작성하기 복잡하기 때문) */
	@Test
	public void xml기반_네임드쿼리를_이용한_조회_테스트() {
		
		/* 1. META-INF 폴더 하위에 menu-query.xml
		 * 2. persistence.xml 파일에 <mapping-file> 추가
		 * 3. activation, jaxb-impl 라이브러리 추가
		 * */
		
		//given
		int menuCodeParameter = 21;
		
		//when
		Menu foundMenu = entityManager.createNamedQuery("section08_menu.selectMenuNameByCode", Menu.class) 
				.setParameter("menuCode", menuCodeParameter)
				.getSingleResult();
		
		//then
		assertNotNull(foundMenu);
		System.out.println("foundMenu = " + foundMenu);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
